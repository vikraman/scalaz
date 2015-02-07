package scalaz

trait Iso[S, T, A, B] {
  def apply[P[_, _], F[_]](p: P[A, F[B]])(implicit F: Functor[F], P: Profunctor[P]): P[S, F[T]]
}

object Iso {
  def iso[S, T, A, B](sa: S => A, bt: B => T): Iso[S, T, A, B] = new Iso[S, T, A, B] {
    def apply[P[_, _], F[_]](p: P[A, F[B]])(implicit F: Functor[F], P: Profunctor[P]): P[S, F[T]] =
      P.dimap(sa)(F.map(bt))(p)
  }
}
