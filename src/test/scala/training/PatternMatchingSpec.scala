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
    it("Should return 'Je me tiens compagnie moi-même' when the list is empty") {
      PatternMatching.listFriends(Nil) shouldBe "Je me tiens compagnie moi-même"
    }

    it("Should return 'Mon meilleur ami est ...' when the list contains exactly one element") {
      PatternMatching.listFriends("Winnie" :: Nil) shouldBe "Mon meilleur ami est Winnie"
    }

    it("Should return 'Mes meilleurs amis sont ... et ...' when the list contains exactly two elements") {
      PatternMatching.listFriends("Laura" :: "Suzanne" :: Nil) shouldBe "Mes meilleurs amis sont Laura et Suzanne"
    }

    it(
      "Should return 'Je suis ami avec ... et ..., mais ... est mon meilleur ami' when the list has more than 2 friends"
    ) {
      PatternMatching.listFriends("John" :: "Paul" :: "Ringo" :: Nil) shouldBe "Je suis ami avec Paul et Ringo, mais John est mon meilleur ami"
      PatternMatching.listFriends("John" :: "Paul" :: "Ringo" :: "George" :: Nil) shouldBe "Je suis ami avec Paul, Ringo et George, mais John est mon meilleur ami"
    }
  }
}
