package tictactoe.server

import fs2._
import fs2.io.tcp._
import cats.effect._
import java.util.concurrent._
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousChannelGroup

object Main extends IOApp {

  val port = 4567
  val host = "0.0.0.0"

  implicit val ec = Executors.newCachedThreadPool()
  implicit val channelGroup = AsynchronousChannelGroup.withThreadPool(ec)

  def run(args: List[String]): IO[ExitCode] = {
    val address = new InetSocketAddress(host, port)
    val startServer = Socket.server[IO](address)

    Stream
      .eval(onStart)
      .flatMap(_ => startServer)
      .evalTap(_ => onConnect)
      .chunkN(2, false)
      .flatMap(startGame)
      .compile
      .drain
      .map(_ => ExitCode.Success)
  }

  private def startGame(chunk: Chunk[Resource[IO, Socket[IO]]]) = {
    val socketsResource: Resource[IO, (Socket[IO], Socket[IO])] = for {
      a <- chunk(0)
      b <- chunk(1)
    } yield (a, b)
    Stream
      .resource(socketsResource)
      .flatMap({
        case (firstPlayer, secondPlayer) => (new Game(firstPlayer, secondPlayer)).stream
      })
  }

  private val onStart = IO {
    println(s"Server is starting on port $host:$port")
  }

  private val onConnect = IO {
    println("Someone is joining")
  }
}
