package scalaz

import Prelude._

trait Enum[A] {
  def all: List[A]
  def succ(a: A): A = all.dropWhile(_ != a).tail.head
  def pred(a: A): A = all.reverse.dropWhile(_ != a).tail.head
  def toEnum(i: Int): A = all.drop(i).head
  def fromEnum(a: A): Int = all.indexOf(a)
}

object Enum {
  def fromList[A](xs: List[A]): Enum[A] = new Enum[A] {
    override def all: List[A] = xs
  }
}
