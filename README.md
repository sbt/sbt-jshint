sbt-jshint
==========

[![Build Status](https://api.travis-ci.org/sbt/sbt-jshint.png?branch=master)](https://travis-ci.org/sbt/sbt-jshint)

Allows JSHint to be used from within sbt. Builds on com.typesafe:js-engine in order to execute jshint.js
along with the scripts to verify. js-engine enables high performance linting given parallelism and native
JS engine execution.

To use this plugin use the addSbtPlugin command within your project's plugins.sbt (or as a global setting) i.e.:

    addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.6")

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project in file(".")).enablePlugins(SbtWeb)

By default linting occurs as part of your project's `jshint` task. Both src/main/assets/\*\*/\*.js and
src/test/assets/\*\*/\*.js sources are linted.

Options can be specified in accordance with the
[JSHint website](http://www.jshint.com/docs) and they share the same set of defaults. To set an option you can
provide a `.jshintrc` file within your project's base directory. If there is no such file then a `.jshintrc` file will
be search for in your home directory. This behaviour can be overridden by using a `JshintKeys.config` setting for the plugin.
`JshintKeys.config` is used to specify the location of a configuration file.

&copy; 2013-2019 [Lightbend Inc.](https://www.lightbend.com)
