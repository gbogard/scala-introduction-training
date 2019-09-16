package training

import cats.effect.IO
import cats.effect.implicits._
import cats.implicits._
import training.CaseClasses.User
import cats.effect.ContextShift
import scala.concurrent.ExecutionContext

object SideEffects {

  /*
   * Un IO[A] est la représentation d'un programme retournant une valeur de type A et pouvant
   * éxécuter des effets de bord tels qu'une connexion à une base de données ou un appel à un service
   * externe.
   *
   * Encapsuler les effets de bords dans des IOs a plusieurs avantages :
      - Le premier, et pas des moindres, c'est de rendre évident pour le lecteur et le compilateur
   * que la fonction est impure. Toute fonction renvoyant un IO est susceptible d'avoir des effets de bord,
   * tandis qu'à l'inverse, les autres fonctions sont réputées pures, ce qui réduit le besoin de lire la
   * documentation ou l'implémantation.
   */

  def fetchUsers(): IO[List[User]] = IO {
    // Fetching from a database ...
    Nil
  }

  /*
   * - Les IOs sont "lazy", l'éxecution des effets de bord est retardée jusqu'au Main de l'application,
   * ce qui donne un plus grand contrôle sur l'éxecution de ces effets de bord. Les IOs sont aussi
   * réferentiellement transparents.
   */
  val log = IO {
    println("Hello there")
  }

  // Pas de log !

  /*
   * - Les IOs peuvent être composés avec `map` et `flatMap` comme les Option et les List,
   * pour créer des programmes plus complexes
   */
  def fetchUserById(id: String): IO[User] = IO {
    User("Florian", 24)
  }
  def fetchFriends(user: User): IO[List[User]] = IO {
    Nil
  }
  def fetchUserAndFriends(id: String): IO[(User, List[User])] =
    fetchUserById(id).flatMap(user => fetchFriends(user).map(friends => (user, friends)))

  /*
   * - Les IOs encapsulent en eux la gestion des eventuels erreurs:
   * lancer une exception dans un IO ne fait pas planter le programme principal
   */
  val error: IO[User] = IO {
    throw new RuntimeException("This user does not exist")
  }

  val userOption: IO[Option[User]] = error
    .map(Option(_))
    .recoverWith({
      case e =>
        IO {
          println(s"An error occured: $e")
          None
        }
    })

  /*
 * - Et enfin, les IOs sont pensés pour la concurrence: plusieurs IOs peuvent s'executer
 * en série sur un seul thread, ou en parallèle sur un pool de threads, avec très peu de modifications
 * au code. 
 * 
 * Cats effect fournit aussi de nombreux outils pour le multithreading tels que des Queue, des
 * Sémaphores, ou encore le type Resource.
 * 
 * Les IOs peuvent aussi être interrompus en cours d'execution et on peut décider de chosir le plus rapide
 * parmi plusieurs IOs pour implémenter des systèmes de Timeout par exemple.
 * 
 * Voici comment on pourrait récuperer une liste d'utilisateurs en parallèle pour obtenir un meilleur
 * temps de réponse:
 */
  implicit def cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val ids = List("48ck", "sdf56", "e5t5", "fk56", "tr89")
  val users: IO[List[User]] = ids.parTraverse(fetchUserById)

  /*
  * ---------------------
  * EXERCICE :
  * 
  * IO.pure permet d'encapsuler une valeur "pure" (sans effet de bord) dans un IO pour la composer avec 
  * d'autres IOs. Vous devez récupérer des données des utilisateurs de manière asynchrone. 
  * Écrivez une fonction qui, en étant donnée
  * - l'id (String) d'un utilisateur
  * - une fonction (String => IO) pour récupérer un utilisateur
  * - un cache (Map[String, User]) contenant des utilisateurs déjà récupérés préalablement
  * récupère l'utilisateur
  * - soit depuis le cache s'il est présent avec IO.pure
  * - soit en éxécutant le programme fourni pour réupérer l'utilisateur depuis une DB
  */
  def fetchUserWithCache(
    userId: String,
    cache: Map[String, User],
    fetchUser: String => IO[User]
  ): IO[User] = cache.get(userId) match {
    case Some(user) => IO.pure(user)
    case None => fetchUser(userId)
  }
}
