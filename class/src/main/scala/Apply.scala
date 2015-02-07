package scalaz

trait Apply[F[_]] {
  val functor: Functor[F]
  def ap[A, B](f: F[A => B]): F[A] => F[B]
}
