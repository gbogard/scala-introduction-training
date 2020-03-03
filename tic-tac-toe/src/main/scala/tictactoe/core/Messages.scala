package tictactoe.core
import tictactoe.core.Domain.Move

object Messages {
  val welcome = "Welcome to the game of Tic Tac Toe!"
  val prompt = "Where do you want to play? Enter the coordinates of your move separated by a space:"
  val waitingForOtherPlayer = "Waiting for your opponent ..."
  val conflict = "This spot is already taken"
  def yourTurn(player: Move) = s"Player $player, your turn!"
  def playerWins(player: Move) = s"🎉 Well done $player, you win! 🎉"
  val youLose = "Game lost! You will do better next time"
  val tie = "Tie! This is worst than losing"
  val invalidInput = "Please input valid coordinates, separated by a space"
}