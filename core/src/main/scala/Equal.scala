package scalaz

trait Equal[A] {
  def equal(a1: A, a2: A): Boolean
}

object Equal {
  def mkEqualUniv[A]: Equal[A] = new Equal[A] { def equal(a1: A, a2: A): Boolean = a1 == a2 }
}
