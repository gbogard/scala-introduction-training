package training

object Functions {

  /*
   * We use the `val` keyword to define a named value
   */
  val a = 10

  /*
   * The type of a named value can generally be inferred by the compiler.
   * However, it can also be set explicitly
   */
  val b: String = "Lorem ipsum dolor sit amet."

  /*
   * Methods are defined with the `def` keyword.
   * The type of every argument must be explicitly set with a colon.
   * The return type of the method can generally be inferred
   */
  def add(a: Int, b: Int) = a + b

  /*
   * Methods in Scala always return something. Some languages make the distinction between
   * *expressions* (things that mean something) and *statements* (things that do something). Scala
   * treats everything as expressions.
   *
   * In Scala, every expression can be reduced to a value, and all values have a type.
   * When an expression is merely used for its side-effects (e.g. write to the standard output),
   * its type will be `Unit`.
   */
  def sayHello(name: String): Unit = println(s"Hello $name!")

  /*
   * Unit is the type having only one concrete value `()`
   */
  val unit: Unit = ()

  /*
   * Since Scala is expression-oriented, control structures such as if/else are also expressions.
   * Since they are expressions, they can be assigned to a value
   */
  def abs(x: Int) = if (x >= 0) x else -x

  /*
   * Curly braces are used to delimit expressions that span multiple lines.
   * The last line of the block will be its effective value.
   *
   * Blocks can be used in a lot of places:
   * In conditions ...
   */
  def isYay = true
  val yayOrNay =
    if (isYay) {
      "Yay!"
    } else {
      "Nay!"
    }

  /*
   * But also in named values:
   */
  val result = {
    val a = 10
    val b = 15
    a + b
  }

  /*
   * --------------------------------
   * EXERCICE:
   *
   * Write a function that takes two arguments of type `Int` and always return the first argument.
   * The second argument must be call-by-name so the program doesn't crash when we pass a dangerous second argument.
   */
  def returnFirst(a: Int, b: => Int) = a
}
