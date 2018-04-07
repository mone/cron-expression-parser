package cronexpressionparser.cli

import cronexpressionparser.models.CronExpression
import cronexpressionparser.BaseSpec
import CronExpressionFormat._

class CronExpressionFormatSpec extends BaseSpec {

  behavior of "EnrichedCronExpression"

  behavior of "EnrichedCronExpression.toMultiLineString"

  it should "print the CronExpression, using 14 columns for the field name" +
    " and a list of space separated elements for the value" in {

    CronExpression(
      Seq(0, 15, 30, 45),
      Seq(0),
      Seq(1, 15),
      1 to 12,
      1 to 5,
      "/usr/bin/find"
    ).toMultiLineString should equal(
      """minute        0 15 30 45
        |hour          0
        |day of month  1 15
        |month         1 2 3 4 5 6 7 8 9 10 11 12
        |day of week   1 2 3 4 5
        |command       /usr/bin/find
      """.stripMargin
    )

  }

}
