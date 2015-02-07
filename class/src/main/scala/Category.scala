package scalaz

trait Category[~>[_, _]] {
  val semigroupoid: Semigroupoid[~>]
  def id[A]: A ~> A
}
