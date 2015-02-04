package scalaz

import std.AllInstances._

import IO._
import Async._

import RTS.defaultRTS.unsafePerformIO_

import TestUtils._

class AsyncSpec extends scalaz.SpecLite {
  "support wait" in {
    val main = for {
      asyncA <- async(constIO(42))
      asyncB <- async(constIO(24))
      a <- await(asyncA)
      b <- await(asyncB)
    } yield (a, b)

    unsafePerformIO_(main) must_=== ((42, 24))
  }

  "support cancel" in {
    var x = false

    val main = for {
      a <- async(threadDelay(1000) *> captureIO(x = true))
      _ <- Async.cancel(a)
    } yield ()

    unsafePerformIO_(main)

    x must_=== false
  }
}
