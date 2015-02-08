package scalaz

abstract class Bounded[A] {
  def min: A
  def max: A
}

object Bounded {
  def apply[A](min0: A, max0: A): Bounded[A] = new Bounded[A] { val min = min0; val max = max0 }
  def minBound[A](implicit A: Bounded[A]): A = A.min
  def maxBound[A](implicit A: Bounded[A]): A = A.max
}

