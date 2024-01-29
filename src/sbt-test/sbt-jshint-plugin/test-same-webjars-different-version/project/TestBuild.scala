import sbt._
import java.util.function.Supplier

object TestBuild {

  class TestLogger(target: File) extends xsbti.Logger {

    def error(msg: Supplier[String]): Unit = {
      if (msg.get().contains("Missing semicolon.")) {
        IO.touch(target / "missing-semi-error")
      }
    }

    def warn(msg: Supplier[String]): Unit = {}
    def info(msg: Supplier[String]): Unit = {}
    def debug(msg: Supplier[String]): Unit = {}
    def trace(t: Supplier[Throwable]): Unit = {}
  }

  class TestReporter(target: File) extends sbt.internal.inc.LoggedReporter(-1, new TestLogger(target))
}
