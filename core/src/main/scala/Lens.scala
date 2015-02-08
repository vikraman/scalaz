package scalaz

abstract class Lens[S, T, A, B] {
  def apply[F[_]](f: A => F[B])(implicit F: Functor[F]): S => F[T]

  def ∘[I, O](i: I)(implicit Compose: Lens.Compose[I, O, S, T, A, B]): O = Compose(this)(i)
}

object Lens {
  trait Types {
    type Lens_[S, A] = Lens[S, S, A, A]
  }

  def lens[S, T, A, B](sa: S => A)(sbt: S => B => T): Lens[S, T, A, B] = new Lens[S, T, A, B] {
    def apply[F[_]](afb: A => F[B])(implicit F: Functor[F]): S => F[T] = s => F.map(sbt(s))(afb(sa(s)))
  }

  def slens[S, A](sa: S => A)(sas: S => A => S): Lens[S, S, A, A] = lens[S, S, A, A](sa)(sas)

  trait Compose[I, O, S, T, A, B] {
    def apply(lens: Lens[S, T, A, B]): I => O
  }

  object Compose {
    implicit def lens[S, T, A, B, C, D]: Compose[Lens[A, B, C, D], Lens[S, T, C, D], S, T, A, B] =
      new Compose[Lens[A, B, C, D], Lens[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Lens[A, B, C, D] => Lens[S, T, C, D] = abcd => new Lens[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Functor[F]): S => F[T] = stab.apply(abcd(afb))
        }
      }

    implicit def traversal[S, T, A, B, C, D]: Compose[Traversal[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] =
      new Compose[Traversal[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Traversal[A, B, C, D] => Traversal[S, T, C, D] = abcd => new Traversal[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Applicative[F]): S => F[T] = stab.apply(abcd(afb))(F.apply.functor)
        }
      }

    implicit def prism[S, T, A, B, C, D]: Compose[Prism[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] =
      new Compose[Prism[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Prism[A, B, C, D] => Traversal[S, T, C, D] = abcd =>
          traversal[S, T, A, B, C, D](stab)(abcd.asTraversal)
      }
  }
}
