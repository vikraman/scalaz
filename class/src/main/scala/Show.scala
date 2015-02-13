package scalaz

abstract class Show[A] {
  def show(a: A): String
}

object Show {
  def mkShow[A](f: A => String): Show[A] = new Show[A] { override def show(a: A) = f(a) }
  def mkShowFromToString[A]: Show[A] = mkShow(_.toString)

  def show[A](a: A)(implicit A: Show[A]): String = A.show(a)

  trait Syntax {
    implicit class ShowOps[A](a: A)(implicit A: Show[A]) {
      def show: String = A.show(a)
    }
  }

  implicit val boolean: Show[Boolean] = mkShowFromToString
  implicit val int: Show[Int] = mkShowFromToString
  implicit val throwable: Show[Throwable] = mkShowFromToString
  implicit val string: Show[String] = mkShowFromToString

  import scala.language.implicitConversions

  implicit def list[A: Show]: Show[List[A]] = mkShow(_.map(show(_)).mkString("(", ", ", ")"))
  implicit def option[A: Show]: Show[Option[A]] = mkShow(_.fold("None")(a => s"Some(${show(a)}"))
  implicit def tuple2[A](implicit showA: Show[A]): Show[(A, A)] =
    mkShow { case (a1, a2) => (showA.show(a1), showA.show(a2)).toString }
}
