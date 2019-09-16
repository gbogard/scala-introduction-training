package tictactoe.server

import fs2._
import cats.effect._
import fs2.io.tcp.Socket
import fs2.concurrent.Queue
import scala.util.Random
import tictactoe.core.Domain._
import tictactoe.core._

class Game(xPlayer: Socket[IO], oPlayer: Socket[IO])(implicit cs: ContextShift[IO]) {

  val stream: Stream[IO, Unit] =
    Stream
      .eval(IO {
        println("Two players are connected")
      })
      .flatMap(_ => gameEventsStream)
      .through(handleGameEvents)
      .drain

  private def handleGameEvents(stream: Stream[IO, GameEvent]): Stream[IO, GameEvent] = {
    stream
      .evalTap(event => IO { println(s"New game event : $event") })
      .evalTap({
        case Welcome =>
          val chunk = stringToChunk(Messages.welcome)
          xPlayer.write(chunk).flatMap(_ => oPlayer.write(chunk))

        case PromptMove(player) =>
          val (currentPlayer, otherPlayer) = selectSockets(player)
          val messageForCurrentPlayer      = stringToChunk(Messages.prompt)
          val messageForOtherPlayer        = stringToChunk(Messages.waitingForOtherPlayer)
          currentPlayer
            .write(messageForCurrentPlayer)
            .flatMap(_ => otherPlayer.write(messageForOtherPlayer))

        case PrintGame(board) =>
          val message = stringToChunk(Serialization.serializeBoard(board))
          xPlayer.write(message).flatMap(_ => oPlayer.write(message))

        case Conflict(player) =>
          val message = stringToChunk(Messages.conflict)
          selectSockets(player)._1.write(message)

        case GameFinished(Some(winner)) =>
          val (winnerSocket, loserSocket) = selectSockets(winner)
          winnerSocket
            .write(stringToChunk(Messages.playerWins(winner)))
            .flatMap(_ => loserSocket.write(stringToChunk(Messages.youLose)))

        case GameFinished(None) =>
          val message = stringToChunk(Messages.tie)
          xPlayer.write(message).flatMap(_ => oPlayer.write(message))

        case _ => IO.unit
      })
  }

  private def createGameLoop(queue: Queue[IO, GameEvent]): IO[Unit] = {
    val welcome                   = queue.enqueue1(Welcome)
    def onFinish(state: Finished) = queue.enqueue1(GameFinished(state.winner))
    val game = Logic
      .game(
        selectPlayer = randomChoosePlayer,
        state => {
          val boardSize   = state.board(0).length
          val (socket, _) = selectSockets(state.currentPlayer)
          def promptAndWait: IO[Position] =
            queue
              .enqueue1(PromptMove(state.currentPlayer))
              .flatMap(_ => socket.read(8))
              .map(chunkToString)
              .map(_.flatMap(Extractors.extractPosition(boardSize)))
              .flatMap({
                case Some(pos) => IO.pure(pos)
                case _         => promptAndWait
              })
          promptAndWait
        },
        onConflict = state => queue.enqueue1(Conflict(state.currentPlayer)),
        inspectGame = state => queue.enqueue1(PrintGame(state.board))
      )

    welcome.flatMap(_ => game).flatMap(onFinish)
  }

  private def gameEventsStream: Stream[IO, GameEvent] = {
    Stream
      .eval(Queue.unbounded[IO, GameEvent])
      .flatMap(queue => {
        Stream.eval(createGameLoop(queue)).drain.merge(queue.dequeue)
      })
  }

  private def randomChoosePlayer: IO[Move] = IO {
    Random.shuffle(List(X, O)).head
  }

  private def selectSockets(player: Move) = player match {
    case X => (xPlayer, oPlayer)
    case O => (oPlayer, xPlayer)
  }

  private def chunkToString(chunkOption: Option[Chunk[Byte]]): Option[String] =
    chunkOption.map(chunk => chunk.toList.map(_.toChar).mkString.trim)

  private def stringToChunk(message: String) = Chunk.bytes(s"$message\r\n".getBytes)
}
