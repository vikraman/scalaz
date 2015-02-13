package scalaz

sealed trait Ordering
object Ordering {
  case object LT extends Ordering
  case object GT extends Ordering
  case object EQ extends Ordering
}

