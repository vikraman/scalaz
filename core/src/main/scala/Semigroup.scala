package scalaz

trait Semigroup[A] {
  def op[A]: A => A => A
}
