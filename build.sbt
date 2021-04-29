import Dependencies._

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    organization := "rocket",
    name := "rocket-science",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := Version.ScalaVersion,
    libraryDependencies ++= http4s ++ circe ++ logback,
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )
