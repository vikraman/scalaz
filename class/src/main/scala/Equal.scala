package scalaz

trait Equal[A] {
  def equal(a1: A, a2: A): Boolean
}

object Equal {
  def mkEqualUniv[A]: Equal[A] = new Equal[A] { def equal(a1: A, a2: A): Boolean = a1 == a2 }

  implicit def equalBoolean: Equal[Boolean] = mkEqualUniv
  implicit def equalInt: Equal[Int] = mkEqualUniv
  implicit def equalThrowable: Equal[Throwable] = mkEqualUniv
  implicit def equalTuple2[A](implicit equalA: Equal[A]): Equal[(A, A)] = new Equal[(A, A)] {
    def equal(a1: (A, A), a2: (A, A)): Boolean = equalA.equal(a1._1, a2._1) && equalA.equal(a1._2, a2._2)
  }
}
