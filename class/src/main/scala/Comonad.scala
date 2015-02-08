package scalaz

abstract class Comonad[F[_]] {
  def extend: Extend[F]
  def extract[A]: F[A] => A
}
