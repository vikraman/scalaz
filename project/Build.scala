import sbt._
import Keys._

object ScalazBuild extends Build {

  def testDeps = Seq("org.scalacheck" %% "scalacheck" % "1.11.6" % "test")

  def scalazPrj(prjName: String) = Project(
    id = prjName,
    base = file(prjName)).settings(
    name := s"scalaz-$prjName",
    libraryDependencies ++= testDeps ++ Seq(
      compilerPlugin("org.spire-math" %% "kind-projector" % "0.5.2")
    )
  )

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).aggregate(prelude, core, clazz, io, rts, examples)

  lazy val prelude = scalazPrj("prelude")
  lazy val core = scalazPrj("core").dependsOn(prelude, clazz)
  lazy val clazz = scalazPrj("class").dependsOn(prelude)

  lazy val io = scalazPrj("io")
  lazy val rts = scalazPrj("rts").dependsOn(core, io, core % "test->test")

  lazy val examples = scalazPrj("examples").dependsOn(core, rts)
}
