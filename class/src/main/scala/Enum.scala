package scalaz

import Prelude._

// TODO Why using SList break meta.Deriving?
//
trait Enum[A] {
  def all: List[A]
  def succ(a: A): A = enum.dropWhile(_ != a).tail.head
  def pred(a: A): A = enum.reverse.dropWhile(_ != a).tail.head
  def toEnum(i: Int): A = enum.drop(i).head
  def fromEnum(a: A): Int = enum.indexOf(a)
}

object Enum {
  def fromList[A](xs: List[A]): Enum[A] = new Enum[A] {
    override def enum: List[A] = xs
  }
}
