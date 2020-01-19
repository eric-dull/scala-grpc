import sbt._
import Keys._

object Common {

  val unusedWarnings = Seq("-Ywarn-unused")

  val commonSettings: Seq[Def.Setting[_]] = Seq[Def.Setting[_]](
      version := "0.1-SNAPSHOT",
      organization := "com.eric",
      scalaVersion := "2.13.1",
      scalacOptions ++= Seq(
          "-encoding",
          "UTF-8",
          "-feature",
          "-deprecation",
          "-unchecked",
          "-Ywarn-dead-code",
          "-Xlint",
          "-language:existentials",
          "-language:higherKinds",
          "-language:implicitConversions",
          //"-Yno-adapted-args",
          "-Ywarn-unused"),
      javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")) ++ Seq(Compile, Test).flatMap(c =>
      scalacOptions in (c, console) --= unusedWarnings)

}
