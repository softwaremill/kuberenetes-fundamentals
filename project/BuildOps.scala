import sbt._

object BuildOps {

  lazy val exposedPort = settingKey[Int]("application exposed port")

}
