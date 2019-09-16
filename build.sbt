import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "training"
ThisBuild / organizationName := "Training"

lazy val root = (project in file("."))
  .settings(
    name := "Scala Training",
    libraryDependencies ++= Seq(catsEffect, scalaTest % "test")
  )

lazy val ticTacToeCore = (project in file("tic-tac-toe"))
  .settings(libraryDependencies ++= Seq(catsEffect, scalaTest % "test"))

lazy val ticTacToeCli = (project in file("tic-tac-toe-cli"))
  .dependsOn(ticTacToeCore)

lazy val ticTacToeNetworkServer = (project in file("tic-tac-toe-server"))
  .dependsOn(ticTacToeCore)
  .settings(libraryDependencies ++= Circe.all ++ Fs2.all)