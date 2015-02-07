package scalaz

import Prelude._

trait Profunctor[F[_, _]] {
  def lmap[A, B, C](f: A => B): F[B, C] => F[A, C] = dimap[A, B, C, C](f)(id)
  def rmap[A, B, C](f: B => C): F[A, B] => F[A, C] = dimap[A, A, B, C](id)(f)
  def dimap[A, B, C, D](f: A => B)(g: C => D): F[B, C] => F[A, D] = lmap(f).andThen(rmap(g))
}

object Profunctor {
  trait Strong[P[_, _]] {
    val profunctor: Profunctor[P]

    def first[A, B, C](pab: P[A, B]): P[(A, C), (B, C)] =
      profunctor.dimap[(A, C), (C, A), (C, B), (B, C)](_.swap)(_.swap)(second(pab))

    def second[A, B, C](pab: P[A, B]): P[(C, A), (C, B)] =
      profunctor.dimap[(C, A), (A, C), (B, C), (C, B)](_.swap)(_.swap)(first(pab))
  }

  trait Choice[P[_, _]] {
    import Either._

    val profunctor: Profunctor[P]

    def left[A, B, C](pab: P[A, B]): P[A \/ C, B \/ C] =
      profunctor.dimap[A \/ C, C \/ A, C \/ B, B \/ C](swap(_))(swap(_))(right(pab))

    def right[A, B, C](pab: P[A, B]): P[C \/ A, C \/ B] =
      profunctor.dimap[C \/ A, A \/ C, B \/ C, C \/ B](swap(_))(swap(_))(left(pab))
  }

  final case class UpStar[F[_], A, B](run: A => F[B])
  object UpStar {
    def profunctor[F[_]](implicit F: Functor[F]): Profunctor[UpStar[F, ?, ?]] = new Profunctor[UpStar[F, ?, ?]] {
      override def lmap[A, B, C](k: A => B): UpStar[F, B, C] => UpStar[F, A, C] = f =>
        UpStar(a => f.run(k(a)))
      override def rmap[A, B, C](k: B => C): UpStar[F, A, B] => UpStar[F, A, C] = f =>
        UpStar(a => F.map(k)(f.run(a)))
      override def dimap[A, B, C, D](ab: A => B)(cd: C => D): UpStar[F, B, C] => UpStar[F, A, D] = bfc =>
        UpStar(a => F.map(cd)(bfc.run(ab(a))))
    }
  }
}
