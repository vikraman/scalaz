package scalaz

sealed trait Order
case object LT extends Order
case object RT extends Order
case object EQ extends Order

trait Ord[A] {
  val equal: Equal[A]
  def order(a1: A, a2: A): Order
}

object Ord {
  def order[A](f: (A, A) => Order)(implicit ev: Equal[A]): Ord[A] = new Ord[A] {
    val equal = ev
    def order(a1: A, a2: A) = f(a1,a2)
  }
}
