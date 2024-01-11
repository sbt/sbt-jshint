resolvers ++= Seq(
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("snapshots"),
    )

addSbtPlugin("com.github.sbt" % "sbt-jshint" % sys.props("project.version"))
