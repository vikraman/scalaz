package scalaz

abstract class Traversal[S, T, A, B] {
  def apply[F[_]](afb: A => F[B])(implicit F: Applicative[F]): S => F[T]
}

object Traversal {

  trait Types {
    type Traversal_[S, A] = Traversal[S, S, A, A]
  }
  object Types extends Types
}
