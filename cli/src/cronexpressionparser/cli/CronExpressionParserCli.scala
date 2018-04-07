package cronexpressionparser.cli

import cronexpressionparser.models.CronExpression
import CronExpressionFormat._

import scala.util.{Failure, Success}

object CronExpressionParserCli extends App {

  // put the all the arguments together in a single string and leave the correct
  // splitting to the CronExpression parser
  CronExpression.fromString(
    args.toList.mkString(" ")
  ) match {
    case Success(expression) => println(expression.toMultiLineString)
    case Failure(e) => println(s"Parsing failed, check your input: ${e.toString}")
  }

}