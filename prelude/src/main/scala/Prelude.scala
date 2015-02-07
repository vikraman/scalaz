package scalaz

trait Prelude {
  type =:=[A, B] = scala.Predef.=:=[A, B]
  type ->[A, B] = scala.Predef.Function[A, B]
  type ~[P[_, _], Q[_, _]] = P[Nothing, Nothing] =:= Q[Nothing, Nothing]

  def id[A](x: A): A = x
}

object Prelude extends Prelude
