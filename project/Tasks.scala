import sbt.taskKey

object Tasks {
  lazy val hello = taskKey[Unit]("Prints 'Hello World'")

}
