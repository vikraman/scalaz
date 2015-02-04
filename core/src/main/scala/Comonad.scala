package scalaz

trait Comonad[F[_]] {
  val extend: Extend[F]
  def extract[A]: F[A] => A
}
