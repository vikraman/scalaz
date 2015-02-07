package scalaz

trait Semigroupoid[~>[_, _]] {
  def compose[A, B, C]: (B ~> C) => (A ~> B) => (A ~> C)
}
