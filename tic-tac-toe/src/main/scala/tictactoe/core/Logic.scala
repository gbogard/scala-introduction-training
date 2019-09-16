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
  def createBoard(size: Int): Board =
    (1 to size).map(_ => (1 to size).map(_ => None))

  /**
    * Un accesseur curryfié pour les Move du plateau.
    */
  def getMove(board: Board)(pos: Position): Option[Move] = board(pos.y)(pos.x)

  /**
    * Retourne un plateau mis à jour avec un coup à la position choisie
    *
    * Vous pouvez utiliser .updated pour obtenir une nouvelle liste avec
    * un élement unique remplacé
    */
  def placeMove(board: Board, pos: Position, move: Move): Board =
    board.updated(pos.y, board(pos.y).updated(pos.x, Some(move)))

  /**
    * Retourne le prochain joueur en fonction du joueur courant
    */
  def getNextPlayer(currentPlayer: Move) = currentPlayer match {
    case X => O
    case O => X
  }

  /**
    * Est-ce que le plateau est plein ?
    */
  def isBoardFull(board: Board): Boolean = {
    def boardSize = board(0).length
    board.flatten.count(_.isDefined) == boardSize * boardSize
  }

  /**
    * Scanne le plateau et retourne le gagnant s'il existe.
    */
  def whoWins(board: Board): Option[Move] = {
    val get      = getMove(board) _
    val size     = board(0).length
    val iterator = 0 until size

    def reduceMoves(moves: Seq[Option[Move]]) =
      moves.reduce((a, b) => if (a == b) a else None)

    val columns: Seq[Seq[Option[Move]]] =
      iterator.map(x => iterator.map(y => get(Position(x, y))))

    val topLeftBottomRightMoves: Seq[Option[Move]] =
      iterator.map(xy => get(Position(xy, xy)))
    val topRightBottomLeftMoves: Seq[Option[Move]] =
      iterator.map(y => get(Position(size - (y + 1), y)))

    val horizontalWin         = board.map(reduceMoves).reduce(_ orElse _)
    val verticalWin           = columns.map(reduceMoves).reduce(_ orElse _)
    val topLeftBottomRightWin = reduceMoves(topLeftBottomRightMoves)
    val topRightBottomLeftWin = reduceMoves(topRightBottomLeftMoves)

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
    def promptAndCheckConflict(s: InProgress): IO[Position] =
      promptMove(s)
        .flatMap(pos => {
          getMove(s.board)(pos) match {
            case Some(_) => onConflict(s).flatMap(_ => promptAndCheckConflict(s))
            case None    => IO.pure(pos)
          }
        })

    currentState match {
      case Initial =>
        selectPlayer
          .map(InProgress(createBoard(size), _))

      case s @ InProgress(board, currentPlayer) =>
        (isBoardFull(board), whoWins(board)) match {
          case (_, Some(winner)) => IO.pure(Finished(Some(winner)))
          case (true, _)         => IO.pure(Finished(None))
          case (_, _) =>
            promptAndCheckConflict(s)
              .map(pos => {
                val updatedBoard = placeMove(board, pos, currentPlayer)
                val nextPlayer   = getNextPlayer(currentPlayer)
                InProgress(updatedBoard, nextPlayer)
              })
        }

      case s: Finished => IO.pure(s)
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
