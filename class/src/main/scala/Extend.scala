package scalaz

abstract class Extend[F[_]] {
  def extend[A, B](f: F[A] => B): F[A] => F[B]
}
