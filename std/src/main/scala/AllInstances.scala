package scalaz
package std

import Show._
import Equal._

// TODO Modularize
object AllInstances {
  implicit def equalTuple2[A](implicit equalA: Equal[A]): Equal[(A, A)] = new Equal[(A, A)] {
    def equal(a1: (A, A), a2: (A, A)): Boolean = equalA.equal(a1._1, a2._1) && equalA.equal(a1._2, a2._2)
  }

  implicit def showTuple2[A](implicit showA: Show[A]): Show[(A, A)] =
    mkShow { case (a1, a2) => (showA.show(a1), showA.show(a2)).toString }

  implicit def equalBoolean: Equal[Boolean] = mkEqualUniv
  implicit def showBoolean: Show[Boolean] = mkShowFromToString

  implicit def equalInt: Equal[Int] = mkEqualUniv
  implicit def showInt: Show[Int] = mkShowFromToString

  implicit def equalThrowable: Equal[Throwable] = mkEqualUniv
  implicit def showThrowable: Show[Throwable] = mkShowFromToString
}
