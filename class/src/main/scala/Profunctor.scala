package scalaz

import Prelude._
import Either._
import Either.Syntax._

trait Profunctor[F[_, _]] {
  def lmap[A, B, C](ab: A => B): F[B, C] => F[A, C] = dimap[A, B, C, C](ab)(id)
  def rmap[A, B, C](bc: B => C): F[A, B] => F[A, C] = dimap[A, A, B, C](id)(bc)
  def dimap[A, B, C, D](ab: A => B)(bc: C => D): F[B, C] => F[A, D] = lmap(ab).andThen(rmap(bc))
}

object Profunctor {
  implicit val fun: Profunctor[->] = new Profunctor[->] {
    override def lmap[A, B, C](ab: A => B): (B => C) => (A => C) = bc => a => bc(ab(a))
    override def rmap[A, B, C](bc: B => C): (A => B) => (A => C) = ab => a => bc(ab(a))
    override def dimap[A, B, C, D](ab: A => B)(cd: C => D): (B => C) => (A => D) = bc => a => cd(bc(ab(a)))
  }

  trait Strong[P[_, _]] {
    def profunctor: Profunctor[P]

    def first[A, B, C](pab: P[A, B]): P[(A, C), (B, C)] =
      profunctor.dimap[(A, C), (C, A), (C, B), (B, C)](_.swap)(_.swap)(second(pab))

    def second[A, B, C](pab: P[A, B]): P[(C, A), (C, B)] =
      profunctor.dimap[(C, A), (A, C), (B, C), (C, B)](_.swap)(_.swap)(first(pab))
  }

  trait Choice[P[_, _]] {
    import Either._

    def profunctor: Profunctor[P]

    def left[A, B, C](pab: P[A, B]): P[A \/ C, B \/ C] =
      profunctor.dimap[A \/ C, C \/ A, C \/ B, B \/ C](swap(_))(swap(_))(right(pab))

    def right[A, B, C](pab: P[A, B]): P[C \/ A, C \/ B] =
      profunctor.dimap[C \/ A, A \/ C, B \/ C, C \/ B](swap(_))(swap(_))(left(pab))
  }

  object Choice {
    implicit val fun: Choice[->] = new Choice[->] {
      val profunctor = Profunctor.fun
      override def left[A, B, C](ab: A => B): A \/ C => B \/ C  = _.fold[B \/ C](a => Left(ab(a)))(Right(_))
      override def right[A, B, C](ab: A => B): C \/ A => C \/ B = _.fold[C \/ B](Left(_))((a => Right(ab(a))))
    }
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

  final case class DownStar[F[_], A, B](run: F[A] => B)
  object DowStar {
    def profunctor[F[_]](implicit F: Functor[F]): Profunctor[DownStar[F, ?, ?]] = new Profunctor[DownStar[F, ?, ?]] {
      override def lmap[A, B, C](k: A => B): DownStar[F, B, C] => DownStar[F, A, C] = fbc =>
        DownStar(fb => fbc.run(F.map(k)(fb)))
      override def rmap[A, B, C](k: B => C): DownStar[F, A, B] => DownStar[F, A, C] = fab =>
        DownStar(fa => k(fab.run(fa)))
      override def dimap[A, B, C, D](ab: A => B)(cd: C => D): DownStar[F, B, C] => DownStar[F, A, D] = fbc =>
        DownStar(fb => cd(fbc.run(F.map(ab)(fb))))
    }
  }
}
