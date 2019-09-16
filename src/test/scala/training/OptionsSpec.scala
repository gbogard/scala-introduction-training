package training

import scala.util.Random

import org.scalatest.{FunSpec, Matchers}

class OptionsSpec extends FunSpec with Matchers {
  describe("toUpperCase") {
    it("Should return a None when given a None") {
      Options.toUpperCase(None) shouldBe None
    }

    it("Should return a Some of an uppercase string when given a Some") {
      val randomString = Random.alphanumeric.take(200).mkString
      Options.toUpperCase(Some(randomString)) shouldBe Some(randomString.toUpperCase)
    }
  }

  describe("getUsername") {
    it("Should return the username when given one") {
      val randomUserName = Random.alphanumeric.take(200).mkString
      Options.getUsername("", "", Some(randomUserName)) shouldBe randomUserName
    }

    it("Should return a concatenation of firstName and lastName when the username is not set") {
      val randomFirstName = Random.alphanumeric.take(10).mkString
      val randomLastName  = Random.alphanumeric.take(10).mkString
      Options.getUsername(randomFirstName, randomLastName, None) shouldBe s"$randomFirstName.$randomLastName"
    }
  }
}
