import sbt._
import scalapb.compiler.Version._
object Dependencies {
  object Versions {
    val netty_version = "2.0.28.Final"
    val error_prone_annotations_version = "2.3.3"
  }
  import Versions._
  val netty_tcnative = "io.netty" % "netty-tcnative" % netty_version
  val netty_tcnative_boringssl_static = "io.netty" % "netty-tcnative-boringssl-static" % netty_version
  val error_prone_annotations = "com.google.errorprone" % "error_prone_annotations" % error_prone_annotations_version
  val protobuf_java = "com.google.protobuf" % "protobuf-java" % protobufVersion
  val grpc_netty = "io.grpc" % "grpc-netty" % grpcJavaVersion
  val scalapb_runtime = "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"
  val scalapb_runtime_grpc = "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapbVersion
  val grpc_deps = Seq(
    netty_tcnative,
    netty_tcnative_boringssl_static,
    protobuf_java,
    grpc_netty exclude ("com.google.errorprone", "error_prone_annotations"),
    scalapb_runtime exclude ("com.google.protobuf", "protobuf-java"),
    scalapb_runtime_grpc exclude ("com.google.protobuf", "protobuf-java") exclude ("com.google.errorprone", "error_prone_annotations"))
}
