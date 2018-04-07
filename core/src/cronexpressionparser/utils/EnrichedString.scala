package cronexpressionparser.utils

import scala.util.{Failure, Success, Try}

object EnrichedString {
  /**
    * Utility value class, enriches String with convert and check function
    */
  implicit class StringToInt(val orig: String) extends AnyVal {

    /**
      * Converts the string to an int and checks it's in the specified range
      * (bounds included)
      * Fails if string is not an int or if it's outside the range
      */
    def toIntInRange(range: Range): Try[Int] = {
      Try {
        orig.toInt
      }.flatMap {
        case num if range.contains(num) =>
          Success(num)
        case num =>
          Failure(new Exception(s"Value $num is outside range $range"))
      }
    }

  }
}
