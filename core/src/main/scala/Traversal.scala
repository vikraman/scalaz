package scalaz

abstract class Traversal[S, T, A, B] {
  def apply[F[_]](afb: A => F[B])(implicit F: Applicative[F]): S => F[T]

  def ∘[U, V](u: U)(implicit Compose: Optic.Compose[Traversal, U, V, S, T, A, B]): V = Compose(this)(u)
}

object Traversal {
  trait Types {
    type Traversal_[S, A] = Traversal[S, S, A, A]
  }
  object Types extends Types

  trait Functions {
    def _head[F[_], A]: Traversal_[F[A], A] =
  }
}
