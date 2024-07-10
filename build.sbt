lazy val `sbt-jshint` = project in file(".")

enablePlugins(SbtWebBase)

sonatypeProfileName := "com.github.sbt.sbt-jshint" // See https://issues.sonatype.org/browse/OSSRH-77819#comment-1203625

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

addSbtJsEngine("1.3.9")

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}
