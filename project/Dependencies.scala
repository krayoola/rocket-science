import sbt._

object Dependencies {
  object Version {
    val Http4s = "0.21.16"
    val ScalaVersion = "2.13.4"
  }

  val http4s: Seq[ModuleID] = Seq(
    "org.http4s"    %% "http4s-blaze-server" % Version.Http4s,
    "org.http4s"      %% "http4s-circe"        % Version.Http4s,
    "org.http4s"      %% "http4s-dsl"          % Version.Http4s)
}