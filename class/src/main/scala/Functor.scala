package scalaz

abstract class Functor[F[_]] {
  def map[A, B](f: A => B): F[A] => F[B]
}

object Functor {
  trait Syntax {
    implicit class FunctorOps[F[_], A](fa: F[A])(implicit F: Functor[F]) {
      def map[B](f: A => B): F[B] = F.map(f)(fa)
    }
  }
}
