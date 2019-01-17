import com.typesafe.config.{Config, ConfigFactory}

enablePlugins(JavaAppPackaging)

BuildOps.exposedPort := {
  val config: Config = ConfigFactory.parseFile((resourceDirectory in Compile).value / "application.conf")
  config.getInt("http.port")
}

lazy val rootSettings = Seq(
  libraryDependencies ++= Dependencies.all,
  name := "kubernetes-fundamentals",
  organization := "com.softwaremill",
  scalaVersion := "2.12.7",
  dockerBaseImage := "openjdk:11.0.1-jre",
  scalafmtOnCompile := true,
  version := "1.0.0"
)

git.formattedShaVersion in ThisBuild := {
  val base   = git.baseVersion.?.value
  val suffix = git.makeUncommittedSignifierSuffix(git.gitUncommittedChanges.value, Some("dirty"))
  git.gitHeadCommit.value.map { sha =>
    git.defaultFormatShaVersion(base, sha.take(7), suffix)
  }
}

lazy val dockerSettings = Seq(
  dockerAliases ++= Seq(dockerAlias.value.withTag(Option("minikube"))),
  dockerExposedPorts := Seq(BuildOps.exposedPort.value),
  version in Docker := git.gitDescribedVersion.value.getOrElse(git.formattedShaVersion.value.getOrElse("latest")),
)

lazy val root = project
  .in(file("."))
  .settings(rootSettings)
  .settings(dockerSettings)