package com.typesafe.sbt.jshint

import sbt._
import sbt.Keys._
import sbt.File
import scala.Some
import com.typesafe.sbt.jse.SbtJsTask
import com.typesafe.sbt.web.SbtWeb

object Import {

  object JshintKeys {

    val jshint = TaskKey[Seq[File]]("jshint", "Perform JavaScript linting.")

    val config = SettingKey[Option[File]]("jshint-config", "The location of a JSHint configuration file.")
    val resolvedConfig = TaskKey[Option[File]]("jshint-resolved-config", "The actual location of a JSHint configuration file if present. If jshint-config is none then the task will seek a .jshintrc in the project folder. If that's not found then .jshintrc will be searched for in the user's home folder. This behaviour is consistent with other JSHint tooling.")

  }

}

/**
 * The sbt plugin plumbing around the JSHint library.
 */
object SbtJSHint extends AutoPlugin {

  override def requires = SbtJsTask

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.JshintKeys._

  override def buildSettings = inTask(jshint)(
    SbtJsTask.jsTaskSpecificUnscopedBuildSettings ++ Seq(
      moduleName := "jshint",
      shellFile := getClass.getClassLoader.getResource("jshint-shell.js")
    )
  )

  override def projectSettings = Seq(
    config := None,
    resolvedConfig := {
      config.value.orElse {
        val JsHintRc = ".jshintrc"
        val projectRc = baseDirectory.value / JsHintRc
        if (projectRc.exists()) {
          Some(projectRc)
        } else {
          val homeRc = file(System.getProperty("user.home")) / JsHintRc
          if (homeRc.exists()) {
            Some(homeRc)
          } else {
            None
          }
        }
      }: Option[File]
    }
  ) ++ inTask(jshint)(
    SbtJsTask.jsTaskSpecificUnscopedProjectSettings ++ Seq(
      Assets / includeFilter := (Assets / jsFilter).value,
      TestAssets / includeFilter := (TestAssets / jsFilter).value,

      jsOptions := resolvedConfig.value.fold("{}")(IO.read(_)),

      Assets / taskMessage := "JavaScript linting",
      TestAssets / taskMessage := "JavaScript test linting"

    )
  ) ++ SbtJsTask.addJsSourceFileTasks(jshint)

}
