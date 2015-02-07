import sbt._
import Keys._

object ScalazBuild extends Build {

  def testDeps = Seq("org.scalacheck" %% "scalacheck" % "1.11.6" % "test")

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).aggregate(prelude, core, clazz, data, io, rts, examples)

  lazy val prelude = Project(
    id = "prelude",
    base = file("prelude")).settings(
    name := "scalaz-prelude",
    libraryDependencies ++= testDeps
  )

  lazy val core = Project(
    id = "core",
    base = file("core")).settings(
    name := "scalaz-core",
    libraryDependencies ++= testDeps
  ).dependsOn(prelude, clazz, data)

  lazy val clazz = Project(
    id = "class",
    base = file("class")).settings(
    name := "scalaz-class",
    libraryDependencies ++= testDeps
  ).dependsOn(prelude)

  lazy val data = Project(
    id = "data",
    base = file("data")).settings(
    name := "scalaz-data",
    libraryDependencies ++= testDeps
  ).dependsOn(prelude)

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
  ).dependsOn(core, io, core % "test->test")

  lazy val examples = Project(
    id = "examples",
    base = file("examples")).settings(
    name := "scalaz-examples",
    libraryDependencies ++= testDeps
  ).dependsOn(core, rts)
}
