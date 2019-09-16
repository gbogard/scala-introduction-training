package training

import training.ADT.{Beatle, George, John, Paul, Ringo}
import training.CaseClasses.User

object PatternMatching {

  /*
   * Le pattern matching est une des fonctionnalités les plus puissantes et les plus utilisés du langage.
   * Il s'agit d'associer une expression, à gauche, à une autre, à droite. L'expression de gauche peut
   * être déstructurée et associée selon des conditions arbitraires.
   *
   * Voyez le comme un switch sous stéroïdes.
   *
   * Voilà une implémentation de FizzBuzz en Scala :
   */
  def fizzbuzz(value: Int) = (value % 3, value % 5) match {
    case (0, 0) => "fizzbuzz"
    case (0, _) => "fizz"
    case (_, 0) => "buzz"
    case _      => value.toString
  }

  /*
   * Le pattern matching est particulièrement utile avec les types somme
   *
   * --------------------------------
   * EXERCICE :
   * Compléter la fonction ci-dessous pour renvoyer le nom de famille de tous
   * les Beatles.
   * (Les résultats attendus sont dans les tests)
   */
  def lastName(beatle: Beatle) = beatle match {
    case John   => "Lennon"
    case Paul   => "McCartney"
    case _ => ???
  }
  // --------------------------------

  /*
   * Il permet de déstructurer des case classes
   */
  case class Message(from: User, to: User, body: Option[String])

  def interceptMessage(message: Message) = message match {
    case Message(from, to, Some(message)) =>
      println(s"${from.name} a envoyé $message à ${to.name}")
    case Message(from, to, None) =>
      println(s"${from.name} a envoyé un message vide à ${to.name}")
  }

  /*
   * Des listes:
   */
  val friends = List("Alex", "Peter", "Jane")

  def enumerate(elements: List[String]): String = elements match {
    case Nil           => ""
    case single :: Nil => single
    case list :+ last  => list.mkString(", ") + " et " + last
  }

  // enumerate(friends)
  // => "Alex, Peter et Jane"

  /*
   * --------------------------------
   * EXERCICE :
   * Créer une fonction qui accepte une liste d'amis et renvoie une phrase :
   *  - "Mon meilleur ami est Alex" si la liste ne contient qu'un ami
   *  - "Mes meilleurs amis sont Alex et Peter" si la liste contient exactement deux amis
   *  - "Je suis ami avec Peter et Jane, mais Alex est mon meilleur ami" si la liste contient trois amis ou plus
   *  (le meilleur ami est le premier de la liste)
   *  - "Je me tiens compagnie moi-même" si la liste ne contient aucun ami
   *
   * Vous pouvez utiliser la fonction enumerate ci-dessus.
   * Rappel sur l'interpolation de String:
   * s"Hello $world" si l'expression est simple
   * s"Hello ${user.name}" si l'expression est composée ou  contient un "."
   */
  def listFriends(friends: List[String]): String = friends match {
    case Nil => ???
  }
  // --------------------------------
}
