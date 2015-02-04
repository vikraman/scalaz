package scalaz

import scala.concurrent.ExecutionContext

import IO._

object TestUtils {
  // TODO Extract those functions in a System library?
  val getLine = captureIO(readLine)
  val putStrLn = liftIO(println _)
  val threadDelay = liftIO(Thread.sleep _)
}
