package acme
package zsyntax

import scalaz._

@z trait ZSyntax {
  def bar[F[_]: Functor, A](xs: F[A]): F[A] = xs.map(id)
  def foo[M[_]: Monad, N[_]: Monad, A](xs: M[A], ys: N[A]): (M[A], N[A]) =
    (bar(xs), bar(ys))

  /*
  def foo[M[_]: Monad, N[_]: Monad, A](xs: M[A], ys: N[A]): (M[A], N[A]) = {
    implicit def ev$1_bind = ev$1.bind
    implicit def ev$1_applicative = ev$1_applicative
    implicit def ev$1_apply = ev$1.apply
    implicit def ev$1_functor = ev$1.functor

    implicit def ev$2_bind = ev$2.bind
    implicit def ev$2_applicative = ev$2_applicative
    implicit def ev$2_apply = ev$2.apply
    implicit def ev$2_functor = ev$2.functor

    (bar(xs), bar(ys))
  }
  */
}
