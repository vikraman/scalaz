package scalaz 

trait Applicative[F[_]] {
  val apply: Apply[F]
  def pure[A](a: A): F[A]
}
