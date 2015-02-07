package scalaz
package system

import scalaz.IO._

package object IO {
  val getLine = captureIO(scala.io.StdIn.readLine)
  val putStrLn = liftIO(scala.Predef.println _)
  val threadDelay = liftIO(Thread.sleep _)
}

