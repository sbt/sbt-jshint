lazy val root = (project in file(".")).enablePlugins(SbtWeb)

WebKeys.reporter := new TestBuild.TestReporter(target.value)

