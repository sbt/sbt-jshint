import sbt.internal.inc.LoggedReporter
import xsbti.Problem

lazy val root = (project in file(".")).enablePlugins(SbtWeb)

WebKeys.reporter := new LoggedReporter(-1, streams.value.log) {
  override def log(problem: Problem): Unit = {
    if (problem.message().contains("Missing semicolon.")) {
      IO.touch(baseDirectory.value / "target" / "missing-semi-error")
    }
  }
}

ThisBuild / scalaVersion := {
  if (sbtVersion.value.startsWith("1.")) "2.12.21" else "3.9.0"
}
