package training

object Options {
  /*
   * Scala uses a special structure to encode values that can be absent
   * The Option[T] type can take one of two possible values
   *   - Some(T) : wraps a value of type T
   *   - None : encodes the absence of a value
   */

  // Look, type alias!
  type Parent = String

  val batmanDad: Option[Parent]  = None
  val batgirlDad: Option[Parent] = Some("Jim Gordon")

  /*
   * A function that may or may not yield a significant value is modeled as a function returning
   * an Option.
   * Technically speaking, functions always return a value.
   */
  def divide(a: Double, b: Double): Option[Double] =
    if (b == 0) None
    else Some(a / b)

  /*
   * One can apply a transformation to an Option using `map`.
   *
   * When you have an Option of A (Option[A])
   * and a function from A to B (A => B)
   * then `map` gives you an Option of B (Option[B])
   *
   * Example :
   */
  def double(a: Option[Int]): Option[Int] =
    a.map(value => value * 2)

  val double12 = double(Some(12))
  // => Some(24)
  val doubleNone = double(None)
  // => None

  /**
    * `getOrElse` returns the value of an Option when it's a `Some`, or a default value otherwise
    */
  def getName(name: Option[String]): String = name.getOrElse("No name")

  val name1 = getName(Some("Greg"))
  // => "Greg"
  val name2 = getName(None)
  // => "No name"

  /*
   * --------------------------------
   * EXERCISE :
   *
   * Write a function taking an `Option[String]` as argument. The return value must be
   * an `Option[String]` where the content of the string has been set to upper-case.
   */
  def toUpperCase(opt: Option[String]): Option[String] = ???

  /*
   * ---------------------
   * EXERCISE :
   *
   * Write a function that takes a firstName (String), a lastName (String) and an optionally-defined
   * username (Option[String]) and returns:
   * - either the username defined by the user if defined
   * - or a default name of the form `firstName.lastName`
   *
   * Look at HelloScala.scala. Prepending a string literal with a small `s` allows t interpolate
   * values inside a string.
   */
  def getUsername(firstName: String, lastName: String, username: Option[String]): String = ???
}
