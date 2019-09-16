package training
import org.scalatest.{FunSpec, Matchers}
import scala.util.Random
import training.CaseClasses.User
import cats.effect.IO

class SideEffectsSpec extends FunSpec with Matchers {
  describe("Fetch users with cache") {
    it("Should return the cached user when present") {
      val randomId = Random.alphanumeric.take(20).mkString
      val user     = User(Random.alphanumeric.take(20).mkString, 20)
      val cache    = Map(randomId -> user)
      SideEffects
        .fetchUserWithCache(
          randomId,
          cache,
          _ => IO.raiseError(new Exception("This is not supposed to be caled"))
        )
        .unsafeRunSync() shouldBe user
    }

    it("Should call the fetchUser program with the right id when the user is not present in cache") {
      val randomId = Random.alphanumeric.take(20).mkString
      val user     = User(Random.alphanumeric.take(20).mkString, 20)
      val fetchUser: String => IO[User] = id =>
        if (id == randomId) IO.pure(user)
        else IO.raiseError(new Exception("Wrong id"))
      SideEffects.fetchUserWithCache(randomId, Map.empty, fetchUser).unsafeRunSync() shouldBe user
    }
  }
}
