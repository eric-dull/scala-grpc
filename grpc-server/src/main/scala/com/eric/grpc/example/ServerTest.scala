package com.eric.grpc.example

import java.util.logging.Logger

import io.grpc.{ Server, ServerBuilder }

import scala.concurrent.{ ExecutionContext, Future }

object ServerTest {
  private val logger = Logger.getLogger(classOf[ServerTest].getName)

  def main(args: Array[String]): Unit = {
    val server = new ServerTest(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 8089
}

class ServerTest(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder
      .forPort(ServerTest.port)
      .addService(GreeterGrpc.bindService(new GreeterImpl, executionContext))
      .build()
      .start
    ServerTest.logger.info("Server started, listening on " + ServerTest.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class GreeterImpl extends GreeterGrpc.Greeter {
    override def sayHello(req: HelloRequest) = {
      val reply = HelloReply(message = "Hello " + req.name)
      Future.successful(reply)
    }
  }

}
