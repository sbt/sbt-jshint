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
  // specs allow [0, 1) but 0.2+ versions want domelementtype > 2 which breaks others
  "org.webjars.npm" % "dom-serializer" % "0.1.1" force(),
  // dom-serializer:0.1.1 wants [1.1.1,2), htmlparser2:3.8.3 wants [1.0,1.1) ...
  "org.webjars.npm" % "entities" % "1.1.1",
  "org.webjars.npm" % "jshint" % "2.10.2" exclude("org.webjars.npm", "entities"),
  "org.webjars" % "strip-json-comments" % "1.0.2-1"
)

addSbtJsEngine("1.3.5")

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}
