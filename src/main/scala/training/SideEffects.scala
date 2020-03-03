package training

import cats.effect.IO
import cats.effect.implicits._
import cats.implicits._
import training.CaseClasses.User
import cats.effect.ContextShift
import scala.concurrent.ExecutionContext

object SideEffects {

  /*
   * An IO[A] represents an asynchronous computation that will eventually return a value of type `A`
   * and may execute some side-effects such as connecting to an external database or service.
   *
   * Encapsulating side-effects inside IO monads has several advantages:
   *  - The first is to make obvious of the reader and for the compiler that the function is impure.
   * Any functions that returns an `IO` is suspected to be impure. On the contrary, functions that don't
   * return an `IO` are expected to be pure.
   *
   * This convention dramatically reduces the risks of runtime crashes and the need to read documentation or
   * implementation
   */

  def fetchUsers(): IO[List[User]] = IO {
    // Fetching from a database ...
    Nil
  }

  /*
   * - IOs are lazy. The execution of side-effects are delayed until the IO is explicitly ran, usually in
   * the Main of the application. This gives greater control over the timing of side-effects.
   *
   * IOs are referentially transparent, which mean they can be defined in any order without fear of
   * executing side effects.
   */
  val log = IO {
    println("Hello there")
  }

  // Look, no log!

  /*
   * - IOs can be transformed with `map` and composed with `flatMap`, just like Options and Lists.
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
   * - IOs can raise and recover exceptions
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
  * Finally, IOs are meant for concurrency. Many IOs can be executed in series on one thread, or in parallel on a pool of
  * threads with very few edits to the code.
  *
  * Cats effect providess many tools for building concurrent applications such as queues, semaphores or automatically
  * cleaned resources.
  *
  * IOs can be interrupted during their execution and we can create races between IOs to return the fastest result.
  * This is useful in implementing timeouts for example.
  *
  * Here is how we could fetch a list of users in parallel to improve response time:
 */
  implicit def cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val ids = List("48ck", "sdf56", "e5t5", "fk56", "tr89")
  val users: IO[List[User]] = ids.parTraverse(fetchUserById)

  /*
  * ---------------------
  * EXERCISE :
  *
  * `IO.pure` is used to lift a pure value (without side-effects) inside an IO to compose it with other IOs.
  * You must fetch data regarding a user,
  *
  * Write a function that, given these arguments:
  * - the id (String) of a user
  * - a function (String => IO[User]) that fetches a user from a database
  * - and a cache (Map[String, User]) containing users that already have been retrieved
  * will fetch a user
  * - from the cache when it's already there
  * - from the database using the provided function when it's not
  *
  * You don't need to care about updating the cache.
  */
  def fetchUserWithCache(
    userId: String,
    cache: Map[String, User],
    fetchUser: String => IO[User]
  ): IO[User] = ???
}
