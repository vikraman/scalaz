package scalaz

import Prelude._

sealed abstract class Either[+A, +B]

object Either {
  final case class Left[A](a: A) extends (A \/ Nothing)
  final case class Right[A](a: A) extends (Nothing \/ A)

  def either[A, B, C](ac: A => C)(bc: B => C): Either[A, B] => C = _ match {
    case Left(l)  => ac(l)
    case Right(r) => bc(r)
  }

  def swap[A, B](ab: Either[A, B]): Either[B, A] = ???

  def fromScala[A, B](ab: scala.Either[A, B]): Either[A, B] = ab.fold(Left(_), Right(_))

  trait Syntax {
    implicit class EitherOps[A, B](ab: Either[A, B]) {
      def fold[C](l: A => C, r: B => C): C = either(l)(r)(ab)
    }
    implicit class ToEitherOps[A](a: A) {
      def left[B]: Either[A, B] = Left(a)
      def right[B]: Either[B, A] = Right(a)
    }

    implicit class EitherAsScalaz[A, B](ab: scala.Either[A, B]) { def asScalaz: Either[A, B] = fromScala(ab) }
  }
}
