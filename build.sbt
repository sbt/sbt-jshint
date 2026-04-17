lazy val scala212 = "2.13.18"
lazy val scala3 = "3.8.3"

ThisBuild / crossScalaVersions := Seq(scala212, scala3)

lazy val `sbt-jshint` = project in file(".")

enablePlugins(SbtWebBase)

description := "Allows JSHint to be used from within sbt"

developers += Developer(
  "playframework",
  "The Play Framework Team",
  "contact@playframework.com",
  url("https://github.com/playframework")
)

libraryDependencies ++= Seq(
  "org.webjars.npm" % "node-require-fallback" % "1.0.0",
  "org.webjars.npm" % "jshint" % "2.13.6", // sync with src/main/resources/jshint-shell.js
  "org.webjars" % "strip-json-comments" % "1.0.2-1", // sync with src/main/resources/jshint-shell.js
)

addSbtJsEngine("1.4.0-M4")

pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" => "1.12.9"
    case _      => "2.0.0-RC11"
  }
}

scalacOptions := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xsource:3", "-release:8")
    case _            => Seq.empty
  }
}

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}
