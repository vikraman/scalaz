package scalaz

trait Choice[P[_, _]] {
  def left[A, B, C](pab: P[A, B]): P[A \/ C, B \/ C]
  def right[A, B, C](pab: P[A, B]): P[C \/ A, C \/ B]
}
