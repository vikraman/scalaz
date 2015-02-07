package scalaz

trait Monoid[A] {
  val semigroup: Semigroup[A]
  val unit: A
}
