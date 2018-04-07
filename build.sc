import mill._
import mill.scalalib._

// scalastyle:off
object testutil extends ScalaModule {
  def scalaVersion = "2.12.4"

  override def ivyDeps = Agg(
    ivy"org.scalatest::scalatest:3.0.5",
    ivy"org.scalacheck::scalacheck:1.13.4",
    ivy"org.mockito:mockito-core:2.16.0"
  )
}

trait CommonModuleDefinition extends ScalaModule {
  def scalaVersion = "2.12.5"

  object test extends Tests {
    override def moduleDeps = super.moduleDeps ++ Seq(testutil)

    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}

object core extends CommonModuleDefinition
