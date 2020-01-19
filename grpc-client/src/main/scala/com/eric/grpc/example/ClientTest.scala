package com.eric.grpc.example

import io.grpc.ManagedChannelBuilder

import scala.concurrent.Future

object ClientTest {
  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val host = "127.0.0.1"
    val port = 8089
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
    val request = HelloRequest(name = "World")
    val blockingStub = GreeterGrpc.blockingStub(channel)
    val reply: HelloReply = blockingStub.sayHello(request)
    println(reply)
    val stub = GreeterGrpc.stub(channel)
    val f: Future[HelloReply] = stub.sayHello(request)
    f onComplete println
  }
}
