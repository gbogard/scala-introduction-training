package tictactoe.cli

import cats.effect._
import tictactoe.core._
import tictactoe.core.Serialization.serializeBoard
import tictactoe.core.Extractors._
import tictactoe.core.Domain._
import scala.util.Random
import scala.io.StdIn

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val size = args.toList match {
      case Int(s) :: Nil => s
      case _             => 3
    }
    val welcome = IO(println(Messages.welcome))
    val onConflict = IO(println(Messages.conflict))
    welcome.flatMap(_ =>
        Logic
      .game(
        selectPlayer = selectRandomPlayer,
        promptMove,
        onConflict = _ => onConflict,
        inspectGame,
        size
      )
    )
      .flatMap(onWin)
      .map(_ => ExitCode.Success)
  }

  private val selectRandomPlayer = IO {
    Random.shuffle(List(X, O)).head
  }

  private def promptMove(state: InProgress) = {
    val boardSize = state.board.length

    val invalidInputMessage       = IO(println(Messages.invalidInput))
    val firstMessage              = IO(println(Messages.yourTurn(state.currentPlayer)))
    val promptMessage: IO[String] = IO(StdIn.readLine(Messages.prompt))

    def prompt: IO[Position] =
      promptMessage
        .map(extractPosition(boardSize))
        .flatMap({
          case Some(pos) => IO.pure(pos)
          case None      => invalidInputMessage.flatMap(_ => prompt)
        })

    firstMessage.flatMap(_ => prompt)
  }

  private def onWin(state: Finished) = IO {
    println(
      state.winner.fold(Messages.tie)(Messages.playerWins)
    )
  }

  private def inspectGame(state: InProgress) = IO {
    print(serializeBoard(state.board))
  }
}
