package scalaz

abstract class Semigroup[A] {
  def op[A]: A => A => A
}
