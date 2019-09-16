package training

import org.scalatest.{FunSpec, Matchers}

class CollectionsSpec extends FunSpec with Matchers {
  describe("toCelsius") {
    it("Should return 0 for 32") {
      Collections.toCelsius(32) shouldBe 0
    }

    it("Should return 17.778 for 64") {
      round(Collections.toCelsius(64)) shouldBe 17.778
    }

    it("Should return 9.444 for 49") {
      round(Collections.toCelsius(49)) shouldBe 9.444
    }

    it("Should return 35 for 95") {
      Collections.toCelsius(95) shouldBe 35
    }

    it("Should apply the method to every element of the list") {
      val input = (2 to 100).map(_.toFloat).toList
      Collections.toCelsius(input) shouldBe (input map Collections.toCelsius)
    }
  }

  describe("temperaturesMean") {
    it("Should return the mean of all thse temperatures") {
      val temps = List(10, 45, 34, 23, 16, 34, 21).map(_.toFloat)
      Collections.temperaturesMean(temps) shouldBe Some(temps.sum / temps.length)
    }

    it("Should return a None when the list is empty") {
      Collections.temperaturesMean(Nil) shouldBe None
    }
  }


  def round(value: Float) = BigDecimal(value).setScale(3, BigDecimal.RoundingMode.HALF_UP).toDouble
}
