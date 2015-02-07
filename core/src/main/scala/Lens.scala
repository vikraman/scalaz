package scalaz

trait Lens[S, T, A, B] {
  def apply[F[_]](f: A => F[B])(implicit F: Functor[F]): S => F[T]
}

object Lens {
  trait Types {
    type Lens_[S, A] = Lens[S, S, A, A]
  }

  def lens[S, T, A, B](sa: S => A)(sbt: S => B => T): Lens[S, T, A, B] = new Lens[S, T, A, B] {
    def apply[F[_]](afb: A => F[B])(implicit F: Functor[F]): S => F[T] = s => F.map(sbt(s))(afb(sa(s)))
  }

  trait Like[F[_], S, T, A, B] { def apply(f: A => F[B]): S => F[T] }
  type  Like_[F[_], A, B] = Like[F, A, A, B, B]
}
