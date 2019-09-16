package training

import scala.util.Random

import org.scalatest.{FunSpec, Matchers}

class FunctionsSpec extends FunSpec with Matchers {
  describe("returnFirst") {
    it("Should return the first of two arguments") {
      val randomInt = Random.nextInt()
      Functions.returnFirst(randomInt, 12) shouldBe randomInt
    }

    it("Should not throw an exception when the second argument throws") {
      noException should be thrownBy {
        def b: Int = throw new RuntimeException("Fails")
        Functions.returnFirst(12, b)
      }
    }
  }
}
