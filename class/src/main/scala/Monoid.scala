package scalaz

abstract class Monoid[A] {
  def semigroup: Semigroup[A]
  def unit: A
}
