package scalaz

import scala.concurrent.Future

import std.AllInstances._

import IO._
import Concurrent._

import RTS.defaultRTS.unsafePerformIO_

import TestUtils._

class RTSSpec extends scalaz.SpecLite {

  "must be stack overflow safe" in {
    def foo(n: Int): Int = foo(n - 1) + 2
    def task(n: Int): IO[Unit] = if (n == 0) unitIO else unitIO *> task(n - 1)

    val size = 65535
    foo(size).mustThrowA[StackOverflowError]

    val main = task(size)

    unsafePerformIO_(main)
  }

  "must support exception handling" in {
    val main = catching(constIO(42))((e: Throwable) => constIO(0))

    unsafePerformIO_(main) must_=== 42
  }

  "must support exception handling (throw)" in {
    case class Foo(x: Int) extends Throwable

    val main = catching(captureIO(throw new Foo(42)) *> constIO(0))((e: Foo) => constIO(e.x))

    unsafePerformIO_(main) must_=== 42
  }

  "must support exception handling (nested)" in {

    case class Foo(x: Int) extends Throwable
    case class Bar(x: Int) extends Throwable

    val main = catching(
      catching(
        captureIO(throw new Foo(42)) *> constIO(0)
      ) { e: Bar => constIO(0) }
    ) { e: Foo => constIO(e.x) }

    unsafePerformIO_(main) must_=== 42
  }

  "must support exception handling (chain)" in {
    case class Foo(x: Int) extends Exception

    val main = catching(captureIO[Int](throw new Foo(42)).map(_ + 1))((e: Foo) => constIO(e.x))

    unsafePerformIO_(main) must_=== 42
  }

  "must support thread interruption" in {
    def task: IO[Unit] = unitIO *> task

    val main = for {
      thread  <- forkIO(task)
      _       <- killThread(thread)
      _       <- waitFuture(thread.future)
    } yield ()

    try {
      unsafePerformIO_(main)
    } catch {
      case e: java.util.concurrent.ExecutionException => e.getCause must_=== ThreadKilled
    }
  }

  "must support legacy future" in {
    import Concurrent._

    val legacyFuture = Future(42)(scala.concurrent.ExecutionContext.Implicits.global)
    val main = concurrently(constIO(24), waitFuture(legacyFuture))

    unsafePerformIO_(main) must_=== ((24, 42))
  }
}
