package scalaz

import Prelude._

sealed abstract class Either[+A, +B]

object Either {
  final case class Left[A](a: A) extends (A \/ Nothing)
  final case class Right[A](a: A) extends (Nothing \/ A)

  def fromScala[A, B](ab: scala.Either[A, B]): Either[A, B] = ab.fold(Left(_), Right(_))

  def swap[A, B](ab: Either[A, B]): Either[B, A] = ???

  trait Syntax {
    implicit class EitherOps[A](a: A) {
      def left[B]: Either[A, B] = Left(a)
      def right[B]: Either[B, A] = Right(a)
    }

    implicit class EitherAsScalaz[A, B](ab: scala.Either[A, B]) { def asScalaz: Either[A, B] = fromScala(ab) }
  }
}
