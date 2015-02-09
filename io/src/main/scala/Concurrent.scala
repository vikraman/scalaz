package scalaz

import Prelude._

import scala.{Seq, Nil}
import scala.concurrent.Future

import IOSpec.Thunk
import IO._

object Concurrent {
  type  ThreadId = IOSpec.ThreadId
  val   ThreadKilled = IOSpec.ThreadKilled
  type  ThreadKilled = IOSpec.ThreadKilled.type

  def forkIO[A](io: IO[A]): IO[ThreadId] =
    IO[ThreadId](Thunk.fork(io))

  def killThread(thread: ThreadId): IO[Unit] =
    captureIO(thread.intr.kill)

  def concurrently[A, B](ioa: IO[A], iob: IO[B]): IO[(A, B)] =
    IO[(A, B)](Thunk.apply[A, B, (A, B)]((a, b) => (a, b))(ioa, iob))

  // TODO Implement with Traverse (+ Ops)
  def mapConcurrently[A, B](ta: Seq[A])(f: A => IO[B])/*(implicit T: Traverse[T])*/: IO[Seq[B]] =  ta match {
    case Nil => constIO(Nil)
    case xs  => xs.map(x => f(x).map(Seq(_))).reduceLeft((a, b) => concurrently(a, b).map { case (a, b) => a ++ b })
  }

  def waitFuture[A](future: Future[A]): IO[A] = IO[A](Thunk.fromFuture(future))
}
