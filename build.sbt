import scalapb.compiler.Version.{ grpcJavaVersion, scalapbVersion }

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

val gRpcDeps = Seq(
  "io.netty" % "netty-tcnative" % "2.0.28.Final",
  "io.netty" % "netty-tcnative-boringssl-static" % "2.0.28.Final",
  "io.grpc" % "grpc-netty" % grpcJavaVersion
  exclude ("com.google.errorprone", "error_prone_annotations"),
  "com.google.protobuf" % "protobuf-java" % "3.8.0",
  "com.google.errorprone" % "error_prone_annotations" % "2.3.3",
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"
  exclude ("com.google.protobuf", "protobuf-java"),
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapbVersion
  exclude ("com.google.protobuf", "protobuf-java")
  exclude ("com.google.errorprone", "error_prone_annotations"))

lazy val root = project.in(file(".")).settings(name := "scala-grpc").aggregate(gRpcServer, gRpcClient, gRpcPB)

lazy val gRpcServer =
  project.in(file("grpc-server")).settings(commonSettings, name := "grpc-server").dependsOn(gRpcPB)

lazy val gRpcClient = project.in(file("grpc-client")).settings(commonSettings, name := "grpc-client").dependsOn(gRpcPB)

lazy val gRpcPB = project
  .in(file("grpc-pb"))
  .settings(
    commonSettings,
    name := "grpc-pb",
    PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value),
    libraryDependencies ++= gRpcDeps)
