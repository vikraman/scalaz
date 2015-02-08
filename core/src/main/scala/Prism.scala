package scalaz

import Prelude._

import Either._
import Maybe._

import Profunctor._
import Optic.Types._

abstract class Prism[S, T, A, B] { self =>
  def apply[P[_, _], F[_]](pafb: P[A, F[B]])(implicit P: Profunctor.Choice[P], F: Applicative[F]): P[S, F[T]]

  def asOptic[P[_, _], F[_]](implicit P: Profunctor.Choice[P], F: Applicative[F]): Optic[P, F, S, T, A, B] = apply(_)

  def asTraversal: Traversal[S, T, A, B] = new Traversal[S, T, A, B] {
    def apply[F[_]](afb: A => F[B])(implicit F: Applicative[F]): S => F[T] = self(afb)
  }
}

object Prism {
  def prism[S, T, A, B](bt: B => T)(seta: S => T \/ A): Prism[S, T, A, B] = new Prism[S, T, A, B] {
    def apply[P[_, _], F[_]](pafb: P[A, F[B]])(implicit P: Profunctor.Choice[P], F: Applicative[F]): P[S, F[T]] = {
      P.profunctor.dimap[S, T \/ A, T \/ F[B], F[T]](seta)(either(F.pure[T])(F.apply.functor.map(bt)))
        .apply(P.right[A, F[B], T](pafb))
    }
  }

  def prismM[S, A, B](bs: B => S)(sma: S => Maybe[A]): Prism[S, S, A, B] =
    prism[S, S, A, B](bs)(s => maybe[A, S \/ A](Left(s))(Right(_))(sma(s)))

  def _just[A]: Prism_[Maybe[A], A] = _Just[A, A]
  def _Just[A, B]: Prism[Maybe[A], Maybe[B], A, B] =
    prism[Maybe[A], Maybe[B], A, B](Just(_))(maybe[A, Maybe[B] \/ A](Left(Empty()))(Right(_)))

  def _Empty[A, B]: Prism_[Maybe[A], Unit] =
    prismM[Maybe[A], Unit, Unit](const(Empty()))(maybe[A, Maybe[Unit]](Just(()))(const(Empty())))

  trait Types {
    type Prism_[S, A] = Prism[S, S, A, A]
  }
  object Types extends Types
}
