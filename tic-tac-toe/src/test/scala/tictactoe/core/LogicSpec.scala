package tictactoe.core

import Domain._
import org.scalatest._
import cats.effect.IO
import scala.util.Random
import cats.effect.concurrent.Ref

class LogicSpec extends FunSpec with Matchers {

  describe("Create Board") {
    it("Should create a matrix of 3x3 filled with Nones") {
      val expected = List(
        List(None, None, None),
        List(None, None, None),
        List(None, None, None)
      )

      Logic.createBoard(3) shouldBe expected
    }

    it("Should create a matrix of 5x5 filled with Nones") {
      val expected = List(
        List(None, None, None, None, None),
        List(None, None, None, None, None),
        List(None, None, None, None, None),
        List(None, None, None, None, None),
        List(None, None, None, None, None)
      )

      Logic.createBoard(5) shouldBe expected
    }
  }

  describe("Place move") {
    it("Should place a cross in the center of this empty 3x3 board") {
      val expected = List(
        List(None, None, None),
        List(None, Some(X), None),
        List(None, None, None)
      )

      val board = Logic.createBoard(3)
      Logic.placeMove(board, Position(1, 1), X) shouldBe expected
    }

    it(
      "Should place a round in the center in the top-right corner of this 3x3 board"
    ) {
      val expected = List(
        List(None, None, Some(O)),
        List(None, None, None),
        List(None, None, None)
      )

      val board = Logic.createBoard(3)
      Logic.placeMove(board, Position(2, 0), O) shouldBe expected
    }

    it(
      "Should place a round in the center in the bottom-left corner of this 3x3 board"
    ) {

      val board = List(
        List(None, None, Some(O)),
        List(None, Some(O), None),
        List(None, None, None)
      )

      val expected = List(
        List(None, None, Some(O)),
        List(None, Some(O), None),
        List(Some(O), None, None)
      )

      Logic.placeMove(board, Position(0, 2), O) shouldBe expected
    }
  }

  describe("Get next player") {
    it("Should return X when the current player is O") {
      Logic.getNextPlayer(O) shouldBe X
    }

    it("Should return O when the current player is X") {
      Logic.getNextPlayer(X) shouldBe O
    }
  }

  describe("Is board full ?") {
    it("Should return false for an empty board") {
      val board = Logic.createBoard(10)
      Logic.isBoardFull(board) shouldBe false
    }

    it("Should return true for a full board") {
      val board = List(
        List(Some(X), Some(O), Some(O)),
        List(Some(O), Some(X), Some(X)),
        List(Some(O), Some(X), Some(O))
      )

      Logic.isBoardFull(board) shouldBe true
    }
  }

  describe("Who wins ?") {
    it("Should return None with an empty board") {
      val board = Logic.createBoard(3)
      Logic.whoWins(board) shouldBe None
    }

    it("Should return X when three X are aligned vertically") {
      val board = List(
        List(Some(X), None, None),
        List(Some(X), None, None),
        List(Some(X), None, None)
      )
      Logic.whoWins(board) shouldBe Some(X)
    }

    it("Should return X when three X are aligned horizontally") {
      val board = List(
        List(Some(X), Some(X), Some(X)),
        List(None, None, None),
        List(None, None, None)
      )
      Logic.whoWins(board) shouldBe Some(X)
    }

    it("Should return O when three O are aligned from top-left to bottom-right") {
      val board = List(
        List(Some(O), None, None),
        List(None, Some(O), None),
        List(None, None, Some(O))
      )
      Logic.whoWins(board) shouldBe Some(O)
    }

    it("Should return O when three O are aligned from top-right to bottom-left") {
      val board = List(
        List(None, None, Some(O)),
        List(None, Some(O), None),
        List(Some(O), None, None)
      )
      Logic.whoWins(board) shouldBe Some(O)
    }
  }

  describe("Get next state") {
    describe("When current state is Initial") {
      it(
        "Should create a board and transition to InProgress with a X player when the player selection program returns X"
      ) {
        val randomSize = randomBoardSize()
        val nextState = Logic
          .getNextState(
            selectPlayer = IO.pure(X),
            promptMove = _ => error,
            onConflict = _ => error,
            randomSize
          )(Initial)
          .unsafeRunSync()

        nextState shouldBe InProgress(Logic.createBoard(randomSize), X)
      }

      it(
        "Should create a board and transition to InProgress with a O player when the player selection program returns X"
      ) {
        val randomSize = randomBoardSize()
        val nextState = Logic
          .getNextState(
            selectPlayer = IO.pure(O),
            promptMove = _ => error,
            onConflict = _ => error,
            randomSize
          )(Initial)
          .unsafeRunSync()

        nextState shouldBe InProgress(Logic.createBoard(randomSize), O)
      }
    }

    describe("When the current state is InProgress") {
      it("Should transition to Finished with a winner when there is a winner") {
        val board: Board = List(List.fill(3)(None), List.fill(3)(None), List.fill(3)(Some(O)))
        val nextState = Logic
          .getNextState(
            selectPlayer = error,
            promptMove = _ => error,
            onConflict = _ => error
          )(InProgress(board, X))
          .unsafeRunSync()
        nextState shouldBe Finished(Some(O))
      }
    }

    it("Should transition to Finished(None) when the board is full without a winner") {
      val board: Board = List(
        List(Some(X), Some(O), Some(X)),
        List(Some(X), Some(X), Some(O)),
        List(Some(O), Some(X), Some(O))
      )
      val nextState = Logic
        .getNextState(
          selectPlayer = error,
          promptMove = _ => error,
          onConflict = _ => error
        )(InProgress(board, X))
        .unsafeRunSync()
      nextState shouldBe Finished(None)
    }

    it(
      "When the board is not full and there is no winner, it should prompt a move and transition to InProgress with an update board"
    ) {
      def test = {
        val randomSize                = randomBoardSize()
        val randomPosition            = Position(Random.nextInt(randomSize), Random.nextInt(randomSize))
        val randomCurrentPlayer: Move = Random.shuffle(X :: O :: Nil).head
        val nextPlayer             = Logic.getNextPlayer(randomCurrentPlayer)
        val board                     = Logic.createBoard(randomSize)
        val nextState = Logic
          .getNextState(
            selectPlayer = error,
            promptMove = _ => IO.pure(randomPosition),
            onConflict = _ => error
          )(InProgress(board, randomCurrentPlayer))
          .unsafeRunSync()
        nextState shouldBe InProgress(Logic.placeMove(board, randomPosition, randomCurrentPlayer), nextPlayer)
      }

      (1 to 500).foreach(_ => test)
    }

    it("Should call onConflict when the user tries to place a move on a non-empty board") {
      val pos = Position(0, 0)
      val board = Logic.placeMove(Logic.createBoard(3), pos, X)
      for {
        onConflictCallRef <- Ref.of[IO, Boolean](false)
        placeMove = (_: InProgress) => onConflictCallRef.get.map {
          case true => Position(1, 1)
          case false => pos
        }
        nextState <- Logic.getNextState(
          selectPlayer = error,
          promptMove = placeMove,
          onConflict = _ => onConflictCallRef.set(true)
        )(InProgress(board, O))
        hasOnConflictBeenCaled <- onConflictCallRef.get
      } yield hasOnConflictBeenCaled shouldBe true
    }
  }

  private def error[A]: IO[A] = IO.raiseError(new Exception("Missing implementation"))
  private def randomBoardSize(): Int = {
    val r = Random.nextInt(100)
    if (r >= 5) r
    else randomBoardSize()
  }
}
