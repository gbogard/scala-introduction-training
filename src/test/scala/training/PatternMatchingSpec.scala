package training

import org.scalatest.{FunSpec, Matchers}
import training.ADT.{Paul, John, George, Ringo}

class PatternMatchingSpec extends FunSpec with Matchers {
  describe("lastName") {
    it("Should return 'McCartney' for Paul") {
      PatternMatching.lastName(Paul) shouldBe "McCartney"
    }

    it("Should return 'Lennon' for John") {
      PatternMatching.lastName(John) shouldBe "Lennon"
    }

    it("Should return 'Starr' for Ringo") {
      PatternMatching.lastName(Ringo) shouldBe "Starr"
    }

    it("Should return 'Harrisson' for George") {
      PatternMatching.lastName(George) shouldBe "Harrisson"
    }
  }

  describe("listFriends") {
    it("Should return 'I don't need anyone's company' when the list is empty") {
      PatternMatching.listFriends(Nil) shouldBe "I don't need anyone's company"
    }

    it("Should return '... is my best friend' when the list contains exactly one element") {
      PatternMatching.listFriends("Winnie" :: Nil) shouldBe "Winnie is my best friend"
    }

    it("Should return 'My best friends are ... and ...' when the list contains exactly two elements") {
      PatternMatching.listFriends("Laura" :: "Suzanne" :: Nil) shouldBe "My best friends are Laura and Suzanne"
    }

    it(
      "Should return 'I am friend with ... and ..., but ... is my best friend' when the list has more than 2 friends"
    ) {
      PatternMatching.listFriends("John" :: "Paul" :: "Ringo" :: Nil) shouldBe "I am friend with Paul and Ringo, but John is my best friend"
      PatternMatching.listFriends("John" :: "Paul" :: "Ringo" :: "George" :: Nil) shouldBe "I am friend with Paul, Ringo and George, but John is my best friend"
    }
  }
}
