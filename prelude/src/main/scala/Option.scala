package scalaz

object Option {
  type Option[A] = scala.Option[A]
  val Some = scala.Some
  val None = scala.None
}
