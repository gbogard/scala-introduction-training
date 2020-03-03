package training

import training.ADT.{Beatle, George, John, Paul, Ringo}
import training.CaseClasses.User

object PatternMatching {

  /*
   * Pattern matching is one of the most widely-used and powerful features of the language.
   * It's used to map a pattern of expressions, on the left side, to an expression, on the right.
   * The left side of the `match` can be used to deconstruct structures and match them according to
   * arbitrary conditions.
   *
   * Structures that can be deconstructed include case classes, lists and tuples. But one can also write an
   * extractor for any type.
   *
   * Think of it as `switch` on steroids
   *
   * Here is an implementation of FizzBuzz using pattern matching
   */
  def fizzbuzz(value: Int) = (value % 3, value % 5) match {
    case (0, 0) => "fizzbuzz"
    case (0, _) => "fizz"
    case (_, 0) => "buzz"
    case _      => value.toString
  }

  /*
   * Pattern matching is especially useful with sum types.
   * The compiler will warn you when a match statement is not exhaustive, preventing
   * undefined behavior at runtime.
   *
   * --------------------------------
   * EXERCISE :
   * Complete the following to return the last names of all the Beatles.
   * (Expected results are in the test suite)
   */
  def lastName(beatle: Beatle) = beatle match {
    case John   => "Lennon"
    case Paul   => "McCartney"
    case _ => ???
  }
  // --------------------------------

  /*
   * Pattern matching can be used to deconstruct and match case classes
   */
  case class Message(from: User, to: User, body: Option[String])

  def interceptMessage(message: Message) = message match {
    case Message(from, to, Some(message)) =>
      println(s"${from.name} has sent $message to ${to.name}")
    case Message(from, to, None) =>
      println(s"${from.name} has sent an empty message to ${to.name}")
  }

  /*
   * Lists:
   */
  val friends = List("Alex", "Peter", "Jane")

  def enumerate(elements: List[String]): String = elements match {
    case Nil           => ""
    case single :: Nil => single
    case list :+ last  => list.mkString(", ") + " and " + last
  }

  // enumerate(friends)
  // => "Alex, Peter et Jane"

  /*
   * --------------------------------
   * EXERCISE :
   *
   * Write a function that takes a list of friends (`List[String]`) and returns a sentence (`String`):
   *  - "Alex is my best friend" when the list has exactly one friend
   *  - "My best friends are Alex and Peter" when the list has exactly two friends
   *  - "I am friend with Peter and Jane, but Alex is my best friend" when the list has three friends or more.
   *  (The best friend is the first one of the list)
   *  - "I don't need anyone's company" when the list is empty
   *
   * You can use the enumerate function above
   *
   * String interpolation reminder
   * s"Hello $world" one the expression is simple
   * s"Hello ${user.name}" when the expression includes a "."
   */
  def listFriends(friends: List[String]): String = friends match {
    case Nil => ???
  }
  // --------------------------------
}
