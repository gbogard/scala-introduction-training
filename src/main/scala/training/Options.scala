package training

object Options {
  /*
   * Scala utilise un type spécial pour encoder l'absence de valeur.
   * Le type Option[T] est un type générique, qui peut prendre deux valeurs possibles:
   *   - Some(T) : encapsule une valeur de type T
   *   - None : encode l'absence de valeur
   */

  // Alias de type!
  type Parent = String

  val batmanDad: Option[Parent]  = None
  val batgirlDad: Option[Parent] = Some("Jim Gordon")

  /*
   * Ainsi, une fonction qui peut renvoyer ou non une valeur est modelisée comme
   * une fonction renvoyant une Option. Techniquement parlant, une fonction renvoie donc
   * toujours une valeur.
   */
  def divide(a: Double, b: Double): Option[Double] =
    if (b == 0) None
    else Some(a / b)

  /*
   * Il est possible d'appliquer une transformation à une option en utilisant `map`.
   *
   * Si vous avez une Option de A
   * et une fonction de A vers B
   * alors `map` vous donne une Option de B
   *
   * Exemple :
   */
  def double(a: Option[Int]): Option[Int] =
    a.map(value => value * 2)

  val double12 = double(Some(12))
  // => Some(24)
  val doubleNone = double(None)
  // => None

  /**
    * `getOrElse` permet de récupérer la valeur de l'option si c'est un Some,
    * ou une valuer par défaut le cas échéant.
    */
  def getName(name: Option[String]): String = name.getOrElse("No name")

  val name1 = getName(Some("Greg"))
  // => "Greg"
  val name2 = getName(None)
  // => "No name"

  /*
   * --------------------------------
   * EXERCICE :
   *
   * Écrire une fonction qui prend une Option[String] en paramètre et renvoie
   * une Option[String] dont le texte est en majuscules.
   */
  def toUpperCase(opt: Option[String]): Option[String] = opt.map(_.toUpperCase)

  /*
   * ---------------------
   * EXERCICE :
   *
   * Écrire une fonction qui prend en paramètres un nom de famille (String),
   * un prénom (String), et un nom d'utilisateur optionnel (Option[String]) et
   * renvoie soit le nom d'utilisateur défini par l'utilisateur, soit un nom
   * d'utilisateur sous la forme "firstName.lastName"
   *
   * Comme dans Hello.scala, un petit "s" avant un String litteral permet d'interpoler
   * des valeurs préfixées par un "$"
   */
  def getUsername(firstName: String, lastName: String, username: Option[String]): String =
    username getOrElse s"$firstName.$lastName"
}
