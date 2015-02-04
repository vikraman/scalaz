package scalaz

trait Monad[F[_]] {
  val bind: Bind[F]
  val applicative: Applicative[F]
}
