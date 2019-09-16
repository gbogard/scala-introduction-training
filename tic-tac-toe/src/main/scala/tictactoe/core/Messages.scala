package tictactoe.core
import tictactoe.core.Domain.Move

object Messages {
  val welcome = "Welcome to the tic-tac-toe game!"
  val prompt = "Où souhaitez vous jouer ? Indiquez les coordonnées, séparées par un espace :"
  val waitingForOtherPlayer = "En attente de l'autre joueur"
  val conflict = "Cette place est déjà occupée"
  def yourTurn(player: Move) = s"Joueur $player, c'est à vous!"
  def playerWins(player: Move) = s"🎉 Bravo ${player}, vous avez gagné! 🎉"
  val youLose = "Perdu! Vous ferez mieux la prochaine fois!"
  val tie = "Égalité! Ex aequo c'est pire que de perdre."
  val invalidInput = "Veuilliez fournir des coordonnées valides, séparées par un espace."
}