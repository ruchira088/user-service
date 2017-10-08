name := """user-service"""
organization := "com.ruchij"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice

// https://mvnrepository.com/artifact/org.reactivemongo/play2-reactivemongo_2.12
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.12" % "0.12.6-play26"

// https://mvnrepository.com/artifact/org.mindrot/jbcrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"

// https://mvnrepository.com/artifact/com.github.etaty/rediscala_2.12
libraryDependencies += "com.github.etaty" % "rediscala_2.12" % "1.8.0"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ruchij.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ruchij.binders._"
