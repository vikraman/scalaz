package scalaz

trait Profunctor[F[_, _]] {
  def lmap[A, B, C](f: A => B): F[B, C] => F[A, C] = dimap[A, B, C, C](f)(id)
  def rmap[A, B, C](f: B => C): F[A, B] => F[A, C] = dimap[A, A, B, C](id)(f)
  def dimap[A, B, C, D](f: A => B)(g: C => D): F[B, C] => F[A, D] = lmap(f).andThen(rmap(g))
}
