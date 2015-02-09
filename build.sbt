version in ThisBuild := "8.0-SNAPSHOT"

organization in ThisBuild := "scalaz"

scalaVersion in ThisBuild := "2.11.5"

scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Ywarn-adapted-args",
  "-Yno-imports",
  "-Yno-predef"
)

resolvers in ThisBuild ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "bintray/non" at "http://dl.bintray.com/non/maven"
)
