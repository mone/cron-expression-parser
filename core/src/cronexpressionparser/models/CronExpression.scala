package cronexpressionparser.models

import cronexpressionparser.utils.TokenParser

import scala.util.{Failure, Try}

/**
  * Representation of a cron expression
  */
case class CronExpression(
  // might want to use tagged types instead of Int everywhere
  minute: Seq[Int],
  hour: Seq[Int],
  dayOfMonth: Seq[Int],
  month: Seq[Int],
  dayOfWeek: Seq[Int],
  command: String
)

object CronExpression {

  // parsers for the different tokens
  val minuteToken = TokenParser(0 to 59)
  val hourToken = TokenParser(0 to 23)
  val domToken = TokenParser(1 to 31)
  val monthToken = TokenParser(1 to 12)
  val dowToken = TokenParser(1 to 7)

  def fromString(expression: String): Try[CronExpression] = {

    val pieces = expression.split(" ", 6).toList

    pieces match {
      case minute :: hour :: dom :: month :: dow :: command :: Nil =>
        // got the right pieces, let's see if they're valid tokens
        for {
          minuteList <- minuteToken.parse(minute)
          hourList <- hourToken.parse(hour)
          domList <- domToken.parse(dom)
          monthList <- monthToken.parse(month)
          dowList <- dowToken.parse(dow)
        } yield CronExpression(
          minuteList,
          hourList,
          domList,
          monthList,
          dowList,
          command // everything else we consider it as part of the command
        )

      case _ =>
        // less pieces then needed, can't parse this
        Failure(new Exception(s"Specified expression is invalid: $expression "))

    }

  }

}



