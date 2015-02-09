package scalaz

abstract class Lens[S, T, A, B] {
  def apply[F[_]](f: A => F[B])(implicit F: Functor[F]): S => F[T]

  def ∘[U, V](u: U)(implicit Compose: Optic.Compose[Lens, U, V, S, T, A, B]): V = Compose(this)(u)
}

object Lens {
  trait Types { type Lens_[S, A] = Lens[S, S, A, A] }

  trait Functions {
    def lens[S, T, A, B](sa: S => A)(sbt: S => B => T): Lens[S, T, A, B] = new Lens[S, T, A, B] {
      def apply[F[_]](afb: A => F[B])(implicit F: Functor[F]): S => F[T] = s => F.map(sbt(s))(afb(sa(s)))
    }

    def slens[S, A](sa: S => A)(sas: S => A => S): Lens[S, S, A, A] = lens[S, S, A, A](sa)(sas)
  }
}
