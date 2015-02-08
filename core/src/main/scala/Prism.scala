package scalaz

import Prelude._

import Either._

import Profunctor._
import Optic.Types._

abstract class Prism[S, T, A, B] {
  def apply[P[_, _], F[_]](pafb: P[A, F[B]])(implicit P: Profunctor.Choice[P], F: Applicative[F]): P[S, F[T]]
  def optic[P[_, _], F[_]](implicit P: Profunctor.Choice[P], F: Applicative[F]): Optic[P, F, S, T, A, B] = apply(_)
}

object Prism {
  def prism[S, T, A, B](bt: B => T)(seta: S => T \/ A): Prism[S, T, A, B] = new Prism[S, T, A, B] {
    def apply[P[_, _], F[_]](pafb: P[A, F[B]])(implicit P: Profunctor.Choice[P], F: Applicative[F]): P[S, F[T]] = {
      P.profunctor.dimap[S, T \/ A, T \/ F[B], F[T]](seta)(either(F.pure[T])(F.apply.functor.map(bt)))
        .apply(P.right[A, F[B], T](pafb))
    }
  }

  trait Types {
    type Prism_[S, A] = Prism[S, S, A, A]
  }
  object Types extends Types
}
