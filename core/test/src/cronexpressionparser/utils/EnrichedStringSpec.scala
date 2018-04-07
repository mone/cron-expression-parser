package cronexpressionparser.utils

import cronexpressionparser.BaseSpec
import EnrichedString._

import scala.util.{Failure, Success}

class EnrichedStringSpec extends BaseSpec {
  import EnrichedStringSpec._

  behavior of "StringToInt"

  behavior of "StringToInt.toIntInRange"

  it should "successfully convert a number within range" in {
    "42".toIntInRange(testRange) should equal (Success(42))
  }

  it should "successfully convert a number that is a boundary of the range" in {
    "41".toIntInRange(testRange) should equal (Success(41))
    "43".toIntInRange(testRange) should equal (Success(43))
  }

  it should "fail conversion if the string is above the range" in {
    "44".toIntInRange(testRange) shouldBe a[Failure[_]]
  }

  it should "fail conversion if the string is below the range" in {
    "40".toIntInRange(testRange) shouldBe a[Failure[_]]
  }

  it should "fail conversion if the string does not represent an integer" in {
    "42.1".toIntInRange(testRange) shouldBe a[Failure[_]]
  }

}

object EnrichedStringSpec {
  val testRange = 41 to 43
}