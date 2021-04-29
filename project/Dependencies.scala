import sbt._

object Dependencies {
  object Version {
    val Http4s: String = "0.21.16"
    val ScalaVersion: String = "2.13.4"
    val CirceVersion: String = "0.12.3"
    val logBackVersion: String = "1.2.3"
  }

  val http4s: Seq[ModuleID] = Seq(
    "org.http4s"    %% "http4s-blaze-server",
    "org.http4s"      %% "http4s-circe",
    "org.http4s"      %% "http4s-dsl").map(_ % Version.Http4s)

  val circe: Seq[ModuleID] =  Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % Version.CirceVersion)

  val logback =    Seq("ch.qos.logback"  %  "logback-classic" % Version.logBackVersion)

}