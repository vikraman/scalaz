package scalaz

import Option._

sealed abstract class Maybe[A]

object Maybe {
  final case class Empty[A]() extends Maybe[A]
  final case class Just[A](a: A) extends Maybe[A]

  def empty[A]: Maybe[A] = Empty()
  def just[A](a: A): Maybe[A] = Just(a)

  def maybe[A, B](n: B)(f: A => B): Maybe[A] => B = _ match {
    case Empty() => n
    case Just(x) => f(x)
  }

  def fromOption[A](oa: Option[A]): Maybe[A] = oa.fold[Maybe[A]](Empty[A])(Just(_))

  trait Syntax {
    implicit class OptionAsMaybe[A](oa: Option[A]) { def asMaybe: Maybe[A] = fromOption(oa) }
  }
}



