package scalaz

abstract class Category[~>[_, _]] {
  def semigroupoid: Semigroupoid[~>]
  def id[A]: A ~> A
}
