package cronexpressionparser.model

import cronexpressionparser.models.CronExpression
import cronexpressionparser.BaseSpec

import scala.util.Failure

class CronExpressionSpec extends BaseSpec {

  behavior of "CronExpression"
  behavior of "CronExpression.fromString"

  it should "parse valid definition" in {
    CronExpression.fromString("*/15 0 1,15 * 1-5 /usr/bin/find")
      .success.value should equal (
        CronExpression(
          Seq(0, 15, 30, 45),
          Seq(0),
          Seq(1, 15),
          Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
          Seq(1, 2, 3, 4, 5),
          "/usr/bin/find"
        )
      )
  }

  it should "parse valid definition with spaces in the command" in {
    CronExpression.fromString("*/15 0 1,15 * 1-5 /usr/bin/find something somewhere")
      .success.value should equal (
        CronExpression(
          Seq(0, 15, 30, 45),
          Seq(0),
          Seq(1, 15),
          Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
          Seq(1, 2, 3, 4, 5),
          "/usr/bin/find something somewhere"
        )
      )
  }

  // this test also helps us check we correctly defined the boundaries per each field
  it should "correctly explode all fields" in {
    CronExpression.fromString("* * * * * whatever")
      .success.value should equal (
      CronExpression(
        0 to 59,
        0 to 23,
        1 to 31,
        1 to 12,
        1 to 7,
        "whatever"
      )
    )
  }

  it should "fail when a token is invalid" in {
    CronExpression.fromString("60 * * * * whatever") shouldBe a[Failure[_]]
    CronExpression.fromString("* 24 * * * whatever") shouldBe a[Failure[_]]
    CronExpression.fromString("* * 0 * * whatever") shouldBe a[Failure[_]]
    CronExpression.fromString("* * * 0 * whatever") shouldBe a[Failure[_]]
    CronExpression.fromString("* * * * 0 whatever") shouldBe a[Failure[_]]
  }

}
