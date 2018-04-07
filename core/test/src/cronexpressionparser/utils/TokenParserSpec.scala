package cronexpressionparser.utils

import cronexpressionparser.BaseSpec

import scala.util.Failure

class TokenParserSpec extends BaseSpec {
  import TokenParserSpec._

  behavior of "TokenParser"

  behavior of "TokenParser.parse"

  it should "explode the * value" in {
    testParser.parse("*").success.value should contain theSameElementsInOrderAs Seq(4, 5, 6, 7, 8)
  }

  it should "correctly read a single value" in {
    testParser.parse("5").success.value should contain theSameElementsInOrderAs Seq(5)
    testParser.parse("4").success.value should contain theSameElementsInOrderAs Seq(4)
    testParser.parse("8").success.value should contain theSameElementsInOrderAs Seq(8)
  }

  it should "fail to read a single value if the value is too big" in {
    testParser.parse("9") shouldBe a[Failure[_]]
  }

  it should "fail to read a single value if the value is too small" in {
    testParser.parse("3") shouldBe a[Failure[_]]
  }

  it should "explode */number format" in {
    testParser.parse("*/3").success.value should contain theSameElementsInOrderAs Seq(4, 7)
    testParser.parse("*/1").success.value should contain theSameElementsInOrderAs Seq(4, 5, 6, 7, 8)
  }

  it should "fail to parse the */0 format" in {
    testParser.parse("*/0") shouldBe a[Failure[_]]
  }

  it should "fail to parse the */number format if number is too big" in {
    testParser.parse("*/9") shouldBe a[Failure[_]]
  }

  it should "explode a range of numbers" in {
    testParser.parse("5-7").success.value should contain theSameElementsInOrderAs Seq(5, 6, 7)
    testParser.parse("4-8").success.value should contain theSameElementsInOrderAs Seq(4, 5, 6, 7, 8)
  }

  it should "fail to explode a range if boundaries are inverted" in {
    testParser.parse("7-5") shouldBe a[Failure[_]]
  }

  it should "fail to explode a range if one boundary is too low" in {
    testParser.parse("3-7") shouldBe a[Failure[_]]
  }

  it should "fail to explode a range if one boundary is too high" in {
    testParser.parse("4-9") shouldBe a[Failure[_]]
  }

  it should "correctly read a list of numbers" in {
    testParser.parse("5,7").success.value should contain theSameElementsInOrderAs Seq(5, 7)
    testParser.parse("7,5").success.value should contain theSameElementsInOrderAs Seq(5, 7)
    testParser.parse("4,5,6,7,8").success.value should contain theSameElementsInOrderAs Seq(4, 5, 6, 7, 8)
  }

  it should "fail to read a list of numbers if one of them is too high" in {
    testParser.parse("5,9") shouldBe a[Failure[_]]
  }

  it should "fail to read a list of numbers if one of them is too low" in {
    testParser.parse("3,8") shouldBe a[Failure[_]]
  }

  it should "fail in case of unexpected formats" in {
    testParser.parse("foo") shouldBe a[Failure[_]]
    testParser.parse("3,") shouldBe a[Failure[_]]
    testParser.parse("*,*") shouldBe a[Failure[_]]
    testParser.parse("*/*") shouldBe a[Failure[_]]
    testParser.parse("*-*") shouldBe a[Failure[_]]
    testParser.parse("*-4") shouldBe a[Failure[_]]
    testParser.parse("4-5-6") shouldBe a[Failure[_]]
    testParser.parse("4.2") shouldBe a[Failure[_]]
  }

}

object TokenParserSpec {
  val testParser = TokenParser(4 to 8)
}