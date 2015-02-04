package scalaz

trait Extend[F[_]] {
  def extend[A, B](f: F[A] => B): F[A] => F[B]
}
