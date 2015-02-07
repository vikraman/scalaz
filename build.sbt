version in ThisBuild := "8.0-SNAPSHOT"

organization in ThisBuild := "scalaz"

scalaVersion in ThisBuild := "2.11.5"

scalacOptions in ThisBuild ++= Seq(
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Ywarn-adapted-args",
  "-Yno-predef"
)

resolvers in ThisBuild += Resolver.sonatypeRepo("releases")
