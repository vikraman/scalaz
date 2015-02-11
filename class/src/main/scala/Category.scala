package scalaz

abstract class Category[~>[_, _]] {
  implicit def semigroupoid: Semigroupoid[~>]
  def id[A]: A ~> A
}
