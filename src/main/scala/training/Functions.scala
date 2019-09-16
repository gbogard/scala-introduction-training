package training

object Functions {

  /*
   * Pour définir une valeur nommée, on utilise val
   *
   * Les valeurs sont immuables par défaut
   */
  val a = 10

  /*
   * Le type d'une valeur peut géneralement être inféré. Il peut
   * toutefois être précisé explicitement.
   */
  val b: String = "Lorem ipsum dolor sit amet."

  /*
   * Les méthodes en Scala sont définies avec le mot-clé `def`. Le type de chaque argument
   * doit être précisé avec `:`. Le type de retour de la fonction peut géneralement être inféré.
   */
  def add(a: Int, b: Int) = a + b

  /*
   * Les fonctions en Scala renvoient toujours quelque chose. Cerains language font la distinction
   * entre les epxressions (je signifie quelque chose) et les statements (je fais quelque chose), pas Scala.
   *
   * En Scala, toutes les expressions peuvent être réduites à une valeur et toutes les valeurs ont un type.
   * Lorsqu'une epxression n'est utilisée que pour ses effets (e.g. écrire dans la console),
   * son type sera Unit.
   */
  def sayHello(name: String): Unit = println(s"Hello $name!")

  /*
   * Unit est le type contenant une seule valeur `()`
   */
  val unit: Unit = ()

  /*
   * Comme Scala est un langage orienté expression, les structures de contrôle aussi sont
   * des expressions, et peuvent être assignés à des valeurs.
   */
  def abs(x: Int) = if (x >= 0) x else -x

  /*
   * Les accolades délimitemt une epxression sur plusieurs lignes.
   * La valeur effective de l'expression sera celle de la dernière ligne
   *
   * Dans des conditions ...
   */
  def isYay = true
  val yayOrNay =
    if (isYay) {
      "Yay!"
    } else {
      "Nay!"
    }

  /*
   * Mais aussi dans des valeurs nommées
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
   * Écrire une fonction qui prend deux arguments de type String, et
   * renvoie toujours le premier argument.
   *
   * Le deuxième argument doit être en call-by-name pour que la méthode ne plante
   * pas s'il le deuxième argument lance une exception.
   */
  def returnFirst(a: Int, b: => Int) = a
}
