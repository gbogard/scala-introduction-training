package training

object CaseClasses {
  /*
   * Case classes are special classes which are used to model immutable records of data.
   * They provide additional features compared to regular classes.
   */
  case class User(name: String, age: Int)

  val claire = User("Claire", 51)
  val amy = User("Amy", 31)

  println(s"Hello ${amy.name}")

  // Case classes are immutable
  // `amy.name = "Foo bar"` will not compile

  // Case classes are structurally compared for equality
  assert(User("John", 20) == User("John", 20))

  // One can `copy` a case class and override specific fields.
  // A copy doesn't change the first case class
  val youngerClaire = claire.copy(age = 25)

  /*
   * Case classes, like methods, can have fields with default values.
   * When instantiating a case class, one can explicitly override the default value of a field.
   */
  case class Server(
      hostname: String,
      environment: String = "production",
      os: String = "Debian"
  )

  val a = Server("1.2.3.4")
  val b = Server("2.3.4.5", "staging")

  // Btw, I use Arch
  val c = Server("3.4.5.6", os = "Arch")

  /*
   * EXERCISE : You are programming an anti-aging cream.
   *  Write a function that takes a User as input, and returns an updated User whose age
   *  has been reduced:
   *   - of 10 years if the person is 60 years or more
   *   - of 5 years if the person is 50 years or more
   *   - of 2 years if the person is 30 years or more
   *   - of 0 when the person is less than 20
   */
  def magicCream(user: User): User =
    if (user.age >= 60) {
      user.copy(age = user.age - 10)
    } else if (user.age >= 50) {
      user.copy(age = user.age - 5)
    } else if(user.age >= 30) {
      user.copy(age = user.age - 2)
    } else {
      user
    }
}
