package scalaz

abstract class Semigroupoid[~>[_, _]] {
  def compose[A, B, C]: (B ~> C) => (A ~> B) => (A ~> C)
}
