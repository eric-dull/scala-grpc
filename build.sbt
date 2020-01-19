import Common._
import Dependencies._
import Tasks._

hello := println("我是一个测试！！！")

lazy val root = project.in(file(".")).settings(name := "scala-grpc").aggregate(gRpcServer, gRpcClient, gRpcPB)

lazy val gRpcServer = project.in(file("grpc-server")).settings(commonSettings, name := "grpc-server").dependsOn(gRpcPB)

lazy val gRpcClient = project.in(file("grpc-client")).settings(commonSettings, name := "grpc-client").dependsOn(gRpcPB)

lazy val gRpcPB = project
  .in(file("grpc-pb"))
  .settings(
    commonSettings,
    name := "grpc-pb",
    PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value),
    libraryDependencies ++= grpc_deps)
