//import sbt.Resolver

name := """BookStoreApp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"
val playVersion = "2.6.11"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

//MySQL
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.45"
libraryDependencies += jdbc

//pac4j
val playPac4jVersion = "5.0.0"
val pac4jVersion = "2.2.1"
libraryDependencies ++= Seq(
  ehcache, // or cacheApi
  "org.pac4j" %% "play-pac4j" % playPac4jVersion,
  "org.pac4j" % "pac4j-http" % pac4jVersion,
//  "org.pac4j" % "pac4j-jwt" % pac4jVersion exclude("commons-io" , "commons-io"),
//  "org.pac4j" % "pac4j-sql" % pac4jVersion,
//  "com.typesafe.play" % "play-cache_2.12" % playVersion,
//  "commons-io" % "commons-io" % "2.4",
  "be.objectify" %% "deadbolt-java" % "2.6.1"
)
//resolvers ++= Seq(Resolver.mavenLocal, "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")
//routesGenerator := InjectedRoutesGenerator