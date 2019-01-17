import sbt._

object Dependencies {

  private val akkaVersion     = "2.5.17"
  private val akkaHttpVersion = "10.1.5"

  private lazy val akka: Seq[ModuleID] = {
    Seq(
      "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j"  % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion
    )
  }
  
  private lazy val logging: Seq[ModuleID] = {
    val logbackVersion = "1.2.3"
    Seq(
      "ch.qos.logback"             % "logback-classic"  % logbackVersion,
      "ch.qos.logback"             % "logback-core"     % logbackVersion,
      "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.0",
      "org.slf4j"                  % "log4j-over-slf4j" % "1.7.25"
    )
  }
  
  private lazy val softwareMill: Seq[ModuleID] = {
    val macwireVersion = "2.3.1"
    Seq(
      "com.softwaremill.macwire" %% "macros" % macwireVersion,
      "com.softwaremill.macwire" %% "util"   % macwireVersion
    )
  }

  private lazy val testing: Seq[ModuleID] = {
    val scalaTestVersion = "3.0.5"
    val mockitoVersion   = "2.23.0"
    Seq(
      "com.typesafe.akka" %% "akka-http-testkit"   % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit"        % akkaVersion,
      "org.mockito"       % "mockito-core"         % mockitoVersion,
      "org.scalatest"     %% "scalatest"           % scalaTestVersion
    ).map(_ % Test)
  }

  lazy val all: Seq[ModuleID] = akka ++ logging ++ softwareMill ++ testing

}
