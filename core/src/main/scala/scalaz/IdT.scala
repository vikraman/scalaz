package scalaz

trait IdTInstances {

  type IdT[M[_], A] = M[A]

  private trait IdTInstances0[F[_]] extends Traverse1[IdT[F, ?]] with Monad[IdT[F, ?]] with Comonad[IdT[F, ?]] with Distributive[IdT[F, ?]] with Zip[IdT[F, ?]] with Unzip[IdT[F, ?]] with Align[IdT[F, ?]] with Cozip[IdT[F, ?]] with Optional[IdT[F, ?]] with Hoist[IdT] {

    def liftM[G[_], A](a: G[A])(implicit G: Monad[G]): IdT[G, A] = a

    def hoist[M[_]: Monad, N[_]](f: M ~> N) =
      new (IdT[M, ?] ~> IdT[N, ?]) {
        def apply[A](fa: IdT[M, A]): IdT[N, A] = f(fa)
      }

    implicit def apply[G[_]](implicit G: Monad[G]): Monad[IdT[G, ?]] = G
  }

}

object IdT extends IdTInstances

// vim: set ts=4 sw=4 et:
