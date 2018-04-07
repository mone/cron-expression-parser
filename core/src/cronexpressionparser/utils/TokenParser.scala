package cronexpressionparser.utils

import cronexpressionparser.utils.EnrichedString._

import scala.util.{Failure, Success, Try}

object TokenParser {
  // regex:s to match the various token formats
  private val singleValueRegex = """(\d+)""".r
  private val everyRegex = """\*/(\d+)""".r
  private val rangeRegex = """(\d+)-(\d+)""".r
  private val sequenceRegex = """^(\d+,?)+""".r
}

/**
  * Parses a Cron token and checks it's validity based on the specified
  * boundaries (boundaries change based on the field we need to check)
  */
case class TokenParser(range: Range) {
  import TokenParser._

  def parse(str: String): Try[Seq[Int]] = str match {
    // * -> all valid values
    case "*" =>
      Success(range)

    // N -> a single value
    case singleValueRegex(numAsStr) =>
      numAsStr.toIntInRange(range).map(Seq(_))

    // */N every N values
    case everyRegex(numAsStr) =>
      numAsStr.toIntInRange(1 to range.max).map(range.min to (range.max, _))

    // N1-N2 range from N1 to N2
    case rangeRegex(fromAsStr, toAsStr) =>
      for {
        from <- fromAsStr.toIntInRange(range)
        to <- toAsStr.toIntInRange(range)
        if from < to
      } yield from to to

    // last available case, comma separated list of values
    case sequenceRegex(_*) =>
      // TODO how to match and extract the sequence to avoid this split?
      val numbers = str.split(",")
        // convert to seq and drop duplicates
        .toSeq.distinct
        // validate the numbers
        .map(_.toIntInRange(range))

      // move from a list of tries to a try of list (and sort the values)
      Try { numbers.map(_.get).sorted }

    case _ =>
      Failure(new Exception(s"Unrecognized token format: $str"))

  }


}
