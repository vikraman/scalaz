package scalaz

abstract class Ord[A] {
  def equal: Equal[A]
  def order(a1: A, a2: A): Order
}

object Ord {
  def apply[A](f: (A, A) => Order)(implicit ev: Equal[A]): Ord[A] = new Ord[A] {
    val equal = ev
    def order(a1: A, a2: A) = f(a1,a2)
  }
}
