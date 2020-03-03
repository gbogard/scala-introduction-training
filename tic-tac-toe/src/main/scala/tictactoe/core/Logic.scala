package tictactoe.core

import tictactoe.core.Domain._
import cats.effect.IO

object Logic {

  /**
    * Creates a Board (a matrix of Option[Move]) of the specified size
    *
    * You can use `List.fill`.
    * You can also use `1 to size` to obtain an Range of integers on which you can `map`
    */
  def createBoard(size: Int): Board = List.fill(size, size)(None)

  /**
    * A getter for a move on the board. This is curried for convenience
    * (we'll get to that later)
    */
  def getMove(board: Board)(pos: Position): Option[Move] = board(pos.y)(pos.x)

  /**
    * Returns an updated board by placing a move at a specific position
    *
    * You can use `.updated` to create a new list by replacing a specific element by
    * its index.
    */
  def placeMove(board: Board, pos: Position, move: Move): Board =
    board.updated(pos.y, board(pos.y).updated(pos.x, Some(move)))

  /**
    * Returns the next player depending on the current player
    */
  def getNextPlayer(currentPlayer: Move): Move = currentPlayer match {
    case O => X
    case X => O
  }

  /**
    * Is there room on the board?
    *
    * One approach would be to flatten the board to get a single list of moves, and then check
    * whether all the spots in the board are full.
    */
  def isBoardFull(board: Board): Boolean = ???

  /**
    * Scanne le plateau et retourne le gagnant s'il existe.
    */
  def whoWins(board: Board): Option[Move] = {
    val get      = getMove(board) _
    val size     = board.length
    val iterator = 0 until size

    // If all moves are the same, returns the move, else returns None
    def reduceMoves(moves: Seq[Option[Move]]): Option[Move] = ???

    // Scans the board column by column
    val columns: Seq[Seq[Option[Move]]] = ???

    // Scans the board from top-left to bottom-right
    val topLeftBottomRightMoves: Seq[Option[Move]] = ???

    // Scans the board from top-right to bottom-left
    val topRightBottomLeftMoves: Seq[Option[Move]] = ???

    val horizontalWin: Option[Move]         = ???
    val verticalWin: Option[Move]           = ???
    val topLeftBottomRightWin: Option[Move] = ???
    val topRightBottomLeftWin: Option[Move] = ???

    horizontalWin orElse verticalWin orElse topLeftBottomRightWin orElse topRightBottomLeftWin
  }

  def getNextState(
      selectPlayer: IO[Move],
      promptMove: InProgress => IO[Position],
      onConflict: InProgress => IO[Unit],
      size: Int = 3
  )(
      currentState: State
  ): IO[State] = {
    currentState match {
      case Initial =>
        // When the state is Initial, we need to run the player selection program,
        // build the board, and transition to InProgress
        ???
      case s @ InProgress(board, currentPlayer) => ???
        // When the state is InProgress, we need to
        // - check whether the board is full -> transition to Finished
        // - check whether there is a winner -> transition to Finished
        // - else prompt a move from the player :
        // --there is already a move in that position -> call the onConflict program and prompt until we get a valid position
        // -- else update the board, select the next player then transition to InProgress
      case s: Finished => 
        // When the state is Finiished, we don't need to transition
        ???
    }
  }

  def game(
      selectPlayer: IO[Move],
      promptMove: InProgress => IO[Position],
      onConflict: InProgress => IO[Unit],
      inspectGame: InProgress => IO[Unit],
      size: Int = 3
  ): IO[Finished] = {
    val nextState = getNextState(selectPlayer, promptMove, onConflict, size) _

    def loop(currentState: State): IO[Finished] = currentState match {
      case Initial       => nextState(Initial).flatMap(loop)
      case s: InProgress => inspectGame(s).flatMap(_ => nextState(s)).flatMap(loop)
      case s: Finished   => IO.pure(s)
    }

    loop(Initial)
  }
}
