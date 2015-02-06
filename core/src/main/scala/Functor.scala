package scalaz

trait Functor[F[_]] {
  def map[A, B](f: A => B): F[A] => F[B]
}
