package training

object Collections {
  /*
   * Lists can store an indeterminate number of ordered values of the same type
   */
  val a = List(1, 2, 3, 4, 10)

  // List equality takes order into account
  List(1, 2, 3, 4, 10) == a // => true

  /*
   * One can also build a list using the :: & Nil syntax
   */
  val b = "foo" :: "bar" :: "baz" :: Nil

  /*
   * A List may be empty
   *
   * They are several ways of defining an empty list
   */
  val c: List[String] = List.empty
  val d: List[String] = Nil
  val e               = List.empty[String]

  c == d // => true
  d == e // => true

  /*
   * One can apply a transformation to a List using `map`, just like on options
   *
   * When you have a List of A (List[A])
   * and a function from A to B (A => B)
   * then `map` gives you a List of B (List[B])
   *
   * Example :
   */
  val doubles = a.map(_ * 2)
  // => List(2, 2, 6, 8, 20)
  // `_ * 2` is equivalent to `a => a * 2`

  /*
   * Lists are indexed starting from zero. One can access a specific element of a list using
   * `list(index)`.
   */
  def getElementUnsafe[A](list: List[A], index: Int): A = list(index)

  /*
   * However, this approach is unsafe!
   * When the required element does not exist, the program fails with an Exception.
   * You should always prefer the `.lift` method which returns an `Option`
   */
  def getElement[A](list: List[A], index: Int): Option[A] = list.lift(index)

  /*
   * --------------------------------
   * EXERCISE : Write a function that returns a list of even numbers from a list of integers
   */
  def getEven(list: List[Int]): List[Int] = list.filter(_ % 2 == 0)

  /*
   * --------------------------------
   * EXERCISE : here is a list of temperatures in Fahrenheit degrees.
   *
   * 1) Write a function to convert from Fahrenheit degrees to Celsius using the formula below :
   *       C = (F - 32) * (5/9)
   * 2) Write a function that returns a list of temperatures in Celsius from a list in Fahrenheit
   * 3) Write a function that returns the mean temperature
   *       Mean = Sum / nb of values
   *    Your function must be *safe* and should never fail.
   */
  def toCelsius(fahrenheit: Float): Float =
    (fahrenheit - 32) * (5F/9)

  def toCelsius(fahrenheit: List[Float]): List[Float] = fahrenheit map toCelsius

  def temperaturesMean(temps: List[Float]): Option[Float] = temps.headOption.map(_ =>
    temps.sum / temps.length
  )
}
