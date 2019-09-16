package training

/*
 * En Scala, il existe deux manières de déclarer le Main d'une application :
 *  - déclarer un `object` qui hérite de `App`. Tout le corps de l'objet sera
 *  évalué au démarrage du programme.
 */
object HelloScala extends App {
  println("Hello World! Bienvenue à cette formation.")
}

/*
 * - ou bien déclarer un objet avec une methode `main` acceptant un tableau de String
 * dans sa signature. C'est la manière d'accéder aux arguments passés à note programme.
 */
object HelloYou {
   def main(args: Array[String]): Unit = {
    // Beaucoup de choses ici, on y reviendra plus tard
    args.headOption.foreach(name => println(s"Hello, $name!"))
  }
}
