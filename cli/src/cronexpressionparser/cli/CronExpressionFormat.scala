package cronexpressionparser.cli

import cronexpressionparser.models.CronExpression

object CronExpressionFormat {

  /**
    * Value class to add formatting options to String
    */
  implicit class EnrichedString(val orig: String) extends AnyVal {

    /**
      * Pads a string to a specified number of characters
      * NOTE: If the string is longer than the specified value it will be cut
      */
    def toNumOfCharacters(num: Int): String = {
      if (orig.size >= num) {
        orig.substring(0, num)
      } else {
        val missing = num - orig.size
        val missingStr = (1 to missing).map(_ => " ").mkString("")
        orig + missingStr
      }
    }

  }

  private val headerColumns = 14
  private val minuteHeader = "minute".toNumOfCharacters(headerColumns)
  private val hourHeader = "hour".toNumOfCharacters(headerColumns)
  private val domHeader = "day of month".toNumOfCharacters(headerColumns)
  private val monthHeader = "month".toNumOfCharacters(headerColumns)
  private val dowHeader = "day of week".toNumOfCharacters(headerColumns)
  private val commandHeader = "command".toNumOfCharacters(headerColumns)

  /**
    * Value class to add formatting options to CronExpression
    */
  implicit class EnrichedCronExpression(val orig: CronExpression) extends AnyVal {

    /**
      * Converts the CronExpression into a multiline string
      * each line containing one of its fields
      */
    def toMultiLineString: String = {
      s"""$minuteHeader${orig.minute.mkString(" ")}
        |$hourHeader${orig.hour.mkString(" ")}
        |$domHeader${orig.dayOfMonth.mkString(" ")}
        |$monthHeader${orig.month.mkString(" ")}
        |$dowHeader${orig.dayOfWeek.mkString(" ")}
        |$commandHeader${orig.command}
      """.stripMargin

    }

  }

}
