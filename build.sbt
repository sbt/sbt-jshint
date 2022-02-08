lazy val `sbt-jshint` = project in file(".")

description := "Allows JSHint to be used from within sbt"

libraryDependencies ++= Seq(
  "org.webjars.npm" % "jshint" % "2.9.3",
  "org.webjars" % "strip-json-comments" % "1.0.2-1"
)

addSbtJsEngine("1.2.3")
