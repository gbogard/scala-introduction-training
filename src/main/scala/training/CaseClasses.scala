package training

object CaseClasses {
  /*
   * Les case classes sont des classes spéciales qui sont utilisées pour modéliser des données
   * immuables.
   * Elles fournissent des fonctionnalités supplémentaires par rapport aux classes classiques
   */

  case class User(name: String, age: Int)

  val thierry = User("Thierry", 51)
  val chantal = User("Chantal", 48)

  println(s"Salut ${thierry.name}")

  // Les case classes sont immuables
  // chantal.name = "Chanchan" ne compile pas

  // L'égalité entre les case classes est faite de manière structurelle
  assert(User("John", 20) == User("John", 20))

  // On peut "copier" une case class et changer seulement certains champs
  val youngerThierry = thierry.copy(age = 25)

  /*
   * Les case classes, comme les fonctions, peuvent inclure des champs par
   * défaut. Il est possible de nommer les champs explicitement lors
   * la construction d'une instance.
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
   * EXERCICE : vous programmez un anti-âge. Écrivez une fonction qui prend un User
   * et réduit son âge
   *   - de 10 ans si la personne a 60 ans ou plus
   *   - 5 ans si la personne a 50 ans ou plus
   *   - 2 ans si la personne a 30 ans ou plus
   *   - 0 si la personne a moins de 30 ans
   */
  def magicCream(user: User): User = ???
}
