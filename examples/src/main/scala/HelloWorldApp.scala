package acme

import scalaz._

import system.IO._

object HelloWorldApp extends App {
  def mainIO: IO[Unit] =
    putStrLn("Hello World")
}
