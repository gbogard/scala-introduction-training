package tictactoe.core

import tictactoe.core.Domain._
import cats.effect.IO

object Logic {

  /**
    * Créé un Board (une matrice de Option[Move]) d'une certain taille.
    *
    * Vous pouvez utiliser (1 to size) pour obtenir un intervalle de nombres
    * sur lequel vous pouvez mapper.
    */
  def createBoard(size: Int): Board = ???

  /**
    * Un accesseur curryfié pour les Move du plateau.
    */
  def getMove(board: Board)(pos: Position): Option[Move] = ???

  /**
    * Retourne un plateau mis à jour avec un coup à la position choisie
    *
    * Vous pouvez utiliser .updated pour obtenir une nouvelle liste avec
    * un élement unique remplacé
    */
  def placeMove(board: Board, pos: Position, move: Move): Board = ???

  /**
    * Retourne le prochain joueur en fonction du joueur courant
    */
  def getNextPlayer(currentPlayer: Move) = ???

  /**
    * Est-ce que le plateau est plein ?
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

    // Scans the board column by volumn
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
      case s @ InProgress(board, currentPlayer) =>
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
