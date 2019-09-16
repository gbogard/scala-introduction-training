import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "1.3.1"

  object Circe {
    private val version = "0.11.0"

    val core = "io.circe" %% "circe-core" % version
    val generic = "io.circe" %% "circe-generic" % version

    val all: Seq[ModuleID] = Seq(core, generic)
  }

  object Fs2 {
    private val version = "1.0.4"

    lazy val core = "co.fs2" %% "fs2-core" % version
    lazy val io = "co.fs2" %% "fs2-io" % version
    val all = Seq(core, io)
  }

}
