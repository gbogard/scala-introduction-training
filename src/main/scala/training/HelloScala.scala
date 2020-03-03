package training

/*
 * In Scala, there are two ways of declaring the entry point of an application:
 * - You can define an object that inherits from the `App` trait, and put your code inside the object like so:
 */
object HelloScala extends App {
  println("Hello World! Welcome to this training session!")
}

/*
 * - or you can define a `main` function that takes an array of `String` as its argument.
 *  This way we can access the command line arguments passed to our application
 */
object HelloYou {
  def main(args: Array[String]) {
    // Lots of things here right now, but we'll cover it later
    args.headOption.foreach(name => println(s"Hello, $name!"))
  }
}
