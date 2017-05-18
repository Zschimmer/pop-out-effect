import Dependencies._

lazy val `pop-out-effect` = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.12.1",
      version      := "0.0.0-SNAPSHOT"
    )),
    name := "pop-out-effect",
    libraryDependencies += scalaTest % Test
  )
