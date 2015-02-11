package acme
package zsyntax

import scalaz._

@z trait ZSyntax {
  def bar[F[_]: Functor, A](xs: F[A]): F[A] = xs.map(id)
  def foo[M[_]: Monad, N[_]: Monad, A](xs: M[A], ys: N[A]): (M[A], N[A]) =
    (bar(xs), bar(ys))
}
