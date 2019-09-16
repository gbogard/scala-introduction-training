package training

object Collections {
  /*
   * Les listes permettent de stocker un nombre indterminé de valeurs ordonnées et
   * de même type.
   */
  val a = List(1, 2, 3, 4, 10)

  // L'égalité entre les listes tient compte de l'ordre des éléments
  List(1, 2, 3, 4, 10) == a // => true

  /*
   * On peut aussi construire une liste avec la syntaxe :: et Nil
   */
  val b = "foo" :: "bar" :: "baz" :: Nil

  /*
   * Une liste peut être vide.
   *
   * Il existe plusieurs manières de déclarer une liste vide :
   */
  val c: List[String] = List.empty
  val d: List[String] = Nil
  val e               = List.empty[String]

  c == d // => true
  d == e // => true

  /*
   * On peut mapper sur les List comme sur les Option :
   * - Si vous avez une List[A]
   * - et une A => B
   * - vous avez une List[B]
   */
  val doubles = a.map(_ * 2)
  // => List(2, 2, 6, 8, 20)

  /*
   * Les listes sont ordonnées à partir de zéro. On peut accéder à un élement d'une liste
   * avec liste(index)
   */
  def getElementUnsafe[A](list: List[A], index: Int): A = list(index)

  /*
   * Seulement cette approche est dangereuse : elle va échouer au runtime si l'index demandé
   * n'existe pas. Pour plus de sécurité, on préferera récupérer une Option[A] grâce à la
   * méthode .lift
   */
  def getElement[A](list: List[A], index: Int): Option[A] = list.lift(index)

  /*
   * --------------------------------
   * EXERCICE : écrire une fonction qui retourne les nombres pairs
   * d'une liste de nombres.
   */
  def getEven(list: List[Int]): List[Int] = ???

  /*
   * --------------------------------
   * EXERCICE : voici une liste de températures en dégrés Fahrenheit.
   *
   * 1) Ëcrire une fonction de conversion de Fahrenheit vers Celsius en utilisant la formule suivante :
   *       C = (F - 32) * (5/9)
   * 2) Écrire la fonction qui à une liste de températures en Fahrenheit associe des températures en Celsius
   * 3) Écrire la fonction qui fait la moyenne des températures
   *       Moyenne = Somme / nombre de valeurs
   *    Votre fonction devra prendre en compte le cas de la division par 0
   */
  def toCelsius(fahrenheit: Float): Float = ???

  def toCelsius(fahrenheit: List[Float]): List[Float] = fahrenheit

  def temperaturesMean(temps: List[Float]): Option[Float] = ???
}
