package scalaz

abstract class Monoid[A] {
  implicit def semigroup: Semigroup[A]
  def unit: A
}
