package scalaz

abstract class Ord[A] {
  implicit def equal: Equal[A]
  def compare(a0: A, a1: A): Ordering
}

object Ord {
  import Ordering._

  def apply[A](f: (A, A) => Ordering)(implicit ev: Equal[A]): Ord[A] = new Ord[A] {
    val equal = ev
    def compare(a0: A, a1: A) = f(a0,a1)
  }

  trait Syntax {
    implicit class OrdOps[A](a0: A)(implicit A: Ord[A]) {
      final def <(a1: A): Boolean = A.compare(a0, a1) == LT
      final def <=(a1: A): Boolean = A.compare(a0, a1) != GT
      final def >(a1: A): Boolean = A.compare(a0, a1) == GT
      final def >=(a1: A): Boolean = A.compare(a0, a1) != LT
      final def max(a1: A): A = if (A.compare(a0, a1) == GT) a0 else a1
      final def min(a1: A): A = if (A.compare(a0, a1) == LT) a0 else a1
    }
  }
  object Syntax extends Syntax
}
