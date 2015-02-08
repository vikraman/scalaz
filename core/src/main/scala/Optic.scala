package scalaz

object Optic {

  trait Types {
    type Optic[P[_, _], F[_], S, T, A, B] = P[A, F[B]] => P[S, F[T]]
    type Optic_[P[_, _], F[_], S, A] = Optic[P, F, S, S, A, A]

    type Optical[P[_, _], Q[_, _], F[_], S, T, A, B] = P[A, F[B]] => Q[S, F[T]]
    type Optical_[P[_, _], Q[_, _], F[_], S, A] = Optical[P, Q, F, S, S, A, A]
  }

  object Types extends Types

  abstract class Compose[O[_, _, _, _], U, V, S, T, A, B] { def apply(lens: O[S, T, A, B]): U => V }

  type Compose_[O[_, _, _, _], P[_, _, _, _], Q[_, _, _, _], S, T, A, B, C, D] =
    Compose[O, P[A, B, C, D], Q[S, T, C, D], S, T, A, B]

  object Compose {
    implicit def lens_lens[S, T, A, B, C, D]: Compose_[Lens, Lens, Lens, S, T, A, B, C, D] =
      new Compose[Lens, Lens[A, B, C, D], Lens[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Lens[A, B, C, D] => Lens[S, T, C, D] = abcd => new Lens[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Functor[F]): S => F[T] = stab.apply(abcd(afb))
        }
      }

    implicit def lens_traversal[S, T, A, B, C, D]: Compose_[Lens, Traversal, Traversal, S, T, A, B, C, D] =
      new Compose[Lens, Traversal[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Traversal[A, B, C, D] => Traversal[S, T, C, D] = abcd => new Traversal[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Applicative[F]): S => F[T] = stab.apply(abcd(afb))(F.apply.functor)
        }
      }

    implicit def lens_prism[S, T, A, B, C, D]: Compose_[Lens, Prism, Traversal, S, T, A, B, C, D] =
      new Compose[Lens, Prism[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Lens[S, T, A, B]): Prism[A, B, C, D] => Traversal[S, T, C, D] = abcd =>
          lens_traversal[S, T, A, B, C, D](stab)(abcd.asTraversal)
      }

    implicit def traversal_lens[S, T, A, B, C, D]: Compose_[Traversal, Lens, Traversal, S, T, A, B, C, D] =
      new Compose[Traversal, Lens[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Traversal[S, T, A, B]): Lens[A, B, C, D] => Traversal[S, T, C, D] = abcd => new Traversal[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Applicative[F]): S => F[T] = stab.apply(abcd(afb)(F.apply.functor))
        }
      }
    implicit def traversal_traversal[S, T, A, B, C, D]: Compose_[Traversal, Traversal, Traversal, S, T, A, B, C, D] =
      new Compose[Traversal, Traversal[A, B, C, D], Traversal[S, T, C, D], S, T, A, B] {
        def apply(stab: Traversal[S, T, A, B]): Traversal[A, B, C, D] => Traversal[S, T, C, D] = abcd => new Traversal[S, T, C, D] {
          def apply[F[_]](afb: C => F[D])(implicit F: Applicative[F]): S => F[T] = stab.apply(abcd(afb))
        }
      }
  }
}
