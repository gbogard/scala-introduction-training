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
  def createBoard(size: Int): Board = List.fill(size, size)(None)

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
  def getNextPlayer(currentPlayer: Move): Move = currentPlayer match {
    case O => X
    case X => O
  }

  /**
    * Est-ce que le plateau est plein ?
    */
  def isBoardFull(board: Board): Boolean = !board.flatten.exists(_.isEmpty)

  /**
    * Scanne le plateau et retourne le gagnant s'il existe.
    */
  def whoWins(board: Board): Option[Move] = {
    val get: Position => Option[Move] = getMove(board) _
    val size                          = board.length
    val iterator                      = 0 until size

    // If all moves are the same, returns the move, else returns None
    def reduceMoves(moves: Seq[Option[Move]]): Option[Move] =
      moves.reduce((a, b) => if (a == b) a else None)

    // Scans the board column by column
    val columns: Seq[Seq[Option[Move]]] =
      iterator.map(x => {
        iterator.map(y => {
          get(Position(x, y))
        })
      })

    // Scans the board from top-left to bottom-right
    val topLeftBottomRightMoves: Seq[Option[Move]] = iterator.map(xy => get(Position(xy, xy)))

    // Scans the board from top-right to bottom-left
    val topRightBottomLeftMoves: Seq[Option[Move]] = iterator.map(y => {
      val x = board.length - y - 1
      get(Position(x, y))
    })

    val horizontalWin: Option[Move]         = board.map(reduceMoves).reduce(_ orElse _)
    val verticalWin: Option[Move]           = columns.map(reduceMoves).reduce((a, b) => a.orElse(b))
    val topLeftBottomRightWin: Option[Move] = reduceMoves(topLeftBottomRightMoves)
    val topRightBottomLeftWin: Option[Move] = reduceMoves(topRightBottomLeftMoves)

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
        // When the state is Initial, we need to run the player selection program,
        // build the board, and transition to InProgress
        selectPlayer.map(player => {
          val board = createBoard(size)
          InProgress(board, player)
        })
      case s @ InProgress(board, currentPlayer) =>
        val full = isBoardFull(board)
        (whoWins(board), full) match {
          case (winner @ Some(_), _) => IO.pure(Finished(winner))
          case (None, true)          => IO.pure(Finished(None))
          case _ =>
            promptMove(s).flatMap(position => {
              getMove(board)(position) match {
                case Some(_) =>
                  onConflict(s).flatMap(
                    _ =>
                      getNextState(
                        selectPlayer,
                        promptMove,
                        onConflict,
                        size
                      )(s)
                  )
                case None =>
                  val updatedBoard = placeMove(board, position, currentPlayer)
                  val nextPlayer   = getNextPlayer(currentPlayer)
                  IO.pure(InProgress(updatedBoard, nextPlayer))
              }
            })
        }
      // When the state is InProgress, we need to
      // - check whether the board is full -> transition to Finished
      // - check whether there is a winner -> transition to Finished
      // - else prompt a move from the player :
      // --there is already a move in that position -> call the onConflict program and prompt until we get a valid position
      // -- else update the board, select the next player then transition to InProgress
      case s: Finished =>
        IO.pure(s)
    }
  }

  def game(
      selectPlayer: IO[Move],
      promptMove: InProgress => IO[Position],
      onConflict: InProgress => IO[Unit],
      inspectGame: InProgress => IO[Unit],
      size: Int = 3
  ): IO[Finished] = {
    val nextState: State => IO[State] = getNextState(selectPlayer, promptMove, onConflict, size) _

    def loop(currentState: State): IO[Finished] = currentState match {
      case Initial       => nextState(Initial).flatMap(loop)
      case s: InProgress => inspectGame(s).flatMap(_ => nextState(s)).flatMap(loop)
      case s: Finished   => IO.pure(s)
    }

    loop(Initial)
  }

}
