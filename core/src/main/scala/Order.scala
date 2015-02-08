package scalaz

sealed trait Order
object Order {
  case object LT extends Order
  case object RT extends Order
  case object EQ extends Order
}

