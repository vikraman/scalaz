package scalaz

trait Prelude {
  type =:=[A, B] = scala.Predef.=:=[A, B]
  type ->[A, B] = scala.Predef.Function[A, B]
  type <:<[A, B] = scala.Predef.<:<[A, B]
  type ~[P[_, _], Q[_, _]] = P[Nothing, Nothing] =:= Q[Nothing, Nothing]
  type \/[A, B] = Either[A, B]

  def ???[A]: A = sys.error("Not implemented yet.")
  def id[A](x: A): A = x
}

object Prelude extends Prelude
