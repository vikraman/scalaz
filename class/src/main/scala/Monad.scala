package scalaz

abstract class Monad[F[_]] {
  implicit def bind: Bind[F]
  implicit def applicative: Applicative[F]

  implicit def apply: Apply[F] = applicative.apply
  implicit def functor: Functor[F] = applicative.apply.functor
}
