package scalaz

sealed abstract class Maybe[A]

object Maybe {
  final case class Empty[A]() extends Maybe[A]
  final case class Just[A](a: A) extends Maybe[A]

  def fromOption[A](oa: Option[A]): Maybe[A] = oa.fold[Maybe[A]](Empty[A])(Just(_))

  trait Syntax {
    implicit class OptionAsMaybe[A](oa: Option[A]) { def asMaybe: Maybe[A] = fromOption(oa) }
  }
}



