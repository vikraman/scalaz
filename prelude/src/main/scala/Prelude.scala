package scalaz

trait Prelude {
  type =:=[A, B] = scala.Predef.=:=[A, B]
  type ->[A, B] = scala.Predef.Function[A, B]
  type <:<[A, B] = scala.Predef.<:<[A, B]
  type ~[P[_, _], Q[_, _]] = P[Nothing, Nothing] =:= Q[Nothing, Nothing]
  type \/[A, B] = Either[A, B]

  def const[A, B](a: A): B => A = _ => a
  def id[A](x: A): A = x

  type AnyVal = scala.AnyVal
  type Array[A] = scala.Array[A]
  type Boolean = scala.Boolean
  type Int = scala.Int
  type Nothing = scala.Nothing
  type String = java.lang.String
  type Unit = scala.Unit

  type Exception = scala.Throwable
  type Throwable = scala.Throwable

  type SList[A] = scala.collection.immutable.List[A]

  val Unit = scala.Unit
}

object Prelude extends Prelude
