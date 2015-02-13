package scalaz
package monad

import scala.annotation.tailrec

import Either._

trait Free[F[_], A] {
  def iter(phi: F[A] => A)(implicit F: Functor[F]): A = Free.iter[F, A](phi)(this)
  def run(implicit ev: Free[F, A] =:= Free[Lazy, A]): A = Free.Trampoline.run[A](this)
}

object Free {
  case class Pure[F[_], A](a: A) extends Free[F, A]
  case class Suspend[F[_], A](ffa: F[Free[F, A]]) extends Free[F, A]

  def iter[F[_]: Functor, A](phi: F[A] => A)(ffa: Free[F, A]): A = ffa match {
    case Pure(a) => a
    case Suspend(m) => phi(m.map(iter(phi)(_)))
  }

  def iterA[G[_]: Applicative, F[_]: Functor, A](phi: F[G[A]] => G[A])(ffa: Free[F, A]): G[A] = ffa match {
    case Pure(a) => a.pure[G]
    case Suspend(m) => phi(m.map(iterA(phi)(_)))
  }

  type Trampoline[A] = Free[Lazy, A]
  object Trampoline {
    def done[A](a: A): Trampoline[A] = Pure[Lazy, A](a)
    def suspend[A](a: => Trampoline[A]): Trampoline[A] = Suspend[Lazy, A](_ => a)

    def run[A](ffa: Free[Lazy, A]): A = {
      @tailrec def go(free: Free[Lazy, A]): A = free match {
        case Pure(a) => a
        case Suspend(m) => go(m.force)
      }
      go(ffa)
    }
  }
}
