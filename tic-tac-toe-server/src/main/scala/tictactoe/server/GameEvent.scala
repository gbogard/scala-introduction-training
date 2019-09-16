package tictactoe.server

import tictactoe.core.Domain._

sealed trait GameEvent

case object Welcome extends GameEvent
case class PromptMove(player: Move) extends GameEvent
case class Conflict(player: Move) extends GameEvent
case class PrintGame(board: Board) extends GameEvent
case object PlayerDisconnected extends GameEvent
case class GameFinished(winner: Option[Move]) extends GameEvent