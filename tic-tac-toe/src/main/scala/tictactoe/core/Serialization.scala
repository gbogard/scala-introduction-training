package tictactoe.core

import tictactoe.core.Domain._

object Serialization {
  private def toEmoji(move: Option[Move]): String = move match {
    case Some(X) => "❌"
    case Some(O) => "⭕"
    case None    => "  "
  }

  def serializeBoard(board: Board): String = {
    val topLine = "┏" + board.head.map(_ => "━━").mkString("┳") + "┓"
    val bottomLine = "┗" + board.head.map(_ => "━━").mkString("┻") + "┛"
    val separator = "\r\n" + "┣" + board.head.map(_ => "━━").mkString("╋") + "┫" + "\r\n"
    val rows = board.map(row => "┃" + row.map(toEmoji).mkString("┃") +"┃").mkString(separator)
    List("", topLine, rows, bottomLine, "").mkString("\r\n")
  }
}
