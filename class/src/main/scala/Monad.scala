package scalaz

abstract class Monad[F[_]] {
  def bind: Bind[F]
  def applicative: Applicative[F]
}
