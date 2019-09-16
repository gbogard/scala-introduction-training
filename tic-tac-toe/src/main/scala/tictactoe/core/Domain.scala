package tictactoe.core

object Domain {

  sealed trait Move
  case object X extends Move
  case object O extends Move

  type Board = Seq[Seq[Option[Move]]]

  sealed trait State
  case object Initial extends State
  case class InProgress(board: Board, currentPlayer: Move) extends State
  case class Finished(winner: Option[Move]) extends State

  case class Position(x: Int, y: Int)
}
