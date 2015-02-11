package scalaz

abstract class Applicative[F[_]] {
  implicit def apply: Apply[F]
  def pure[A](a: A): F[A]

  implicit def functor: Functor[F] = apply.functor
}
