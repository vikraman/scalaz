package scalaz

trait Show[A] {
  def show(a: A): String
}

object Show {
  def mkShow[A](f: A => String): Show[A] = new Show[A] { override def show(a: A) = f(a) }
  def mkShowFromToString[A]: Show[A] = mkShow(_.toString)
}
