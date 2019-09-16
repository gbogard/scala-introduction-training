package training

import org.scalatest.{FunSpec, Matchers}
import training.CaseClasses.{User, magicCream}

class CaseClassesSpec extends FunSpec with Matchers {
  describe("magicCream") {
    it("Should return the user when their age is below 30") {
      val user = User("Gary", 29)
      magicCream(user) shouldBe user
    }

    it("Should subtract 2 years from the user when they are 30-50") {
      val user1 = User("Peter", 31)
      val user2 = User("Fiona", 46)
      magicCream(user1) shouldBe User("Peter", 29)
      magicCream(user2) shouldBe User("Fiona", 44)
    }

    it("Should subtract 5 years from their age when they are 50-60") {
      val user1 = User("Celine", 53)
      val user2 = User("Richard", 56)
      magicCream(user1) shouldBe User("Celine", 48)
      magicCream(user2) shouldBe User("Richard", 51)
    }

    it("Should subtract 10 years from their age when they are 60+") {
      val user1 = User("Alice", 62)
      val user2 = User("Harry", 75)
      magicCream(user1) shouldBe User("Alice", 52)
      magicCream(user2) shouldBe User("Harry", 65)
    }
  }
}
