import sbt._
import Keys._

object ScalazBuild extends Build {

  def testDeps = Seq("org.scalacheck" %% "scalacheck" % "1.11.6" % "test")

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).aggregate(core, data, io, rts)

  lazy val core = Project(
    id = "core",
    base = file("core")).settings(
    name := "scalaz-core",
    libraryDependencies ++= testDeps
  )

  lazy val data = Project(
    id = "data",
    base = file("data")).settings(
    name := "scalaz-data",
    libraryDependencies ++= testDeps
  ).dependsOn(core)

  lazy val std = Project(
    id = "std",
    base = file("std")).settings(
    name := "scalaz-std",
    libraryDependencies ++= testDeps
  ).dependsOn(core)

  lazy val io = Project(
    id = "io",
    base = file("io")).settings(
    name := "scalaz-io",
    libraryDependencies ++= testDeps
  )

  lazy val rts = Project(
    id = "rts",
    base = file("rts")).settings(
    name := "scalaz-rts",
    libraryDependencies ++= testDeps
  ).dependsOn(core, io, core % "test->test", std % "test->compile")
}
