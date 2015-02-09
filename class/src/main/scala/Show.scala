package scalaz

abstract class Show[A] {
  def show(a: A): String
}

object Show {
  def mkShow[A](f: A => String): Show[A] = new Show[A] { override def show(a: A) = f(a) }
  def mkShowFromToString[A]: Show[A] = mkShow(_.toString)

  implicit def showBoolean: Show[Boolean] = mkShowFromToString
  implicit def showInt: Show[Int] = mkShowFromToString
  implicit def showThrowable: Show[Throwable] = mkShowFromToString
  implicit def showTuple2[A](implicit showA: Show[A]): Show[(A, A)] =
    mkShow { case (a1, a2) => (showA.show(a1), showA.show(a2)).toString }

}
