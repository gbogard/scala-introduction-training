package tictactoe.core

import tictactoe.core.Domain.Position

object Extractors {
  object Int {
    def unapply(s: String): Option[Int] =
      try {
        Some(s.toInt)
      } catch {
        case _: java.lang.NumberFormatException => None
      }
  }

  def extractPosition(boardSize: Int)(line: String): Option[Position] =
    line.split(" ").toList match {
      case Int(x) :: Int(y) :: Nil
          if (0 <= x && x < boardSize && 0 <= y && y < boardSize) =>
        Some(Position(x, y))
      case _ => None
    }

}
