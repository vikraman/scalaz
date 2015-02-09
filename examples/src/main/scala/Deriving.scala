package acme
package deriving

import scalaz._

object Demo {
  sealed trait Foo
  case object Bar extends Foo
  case object Baz extends Foo

  val enum: Enum[Foo] = Deriving.mkEnum[Foo]

  // scala> acme.deriving.Demo.enum.all
  // res0: List[acme.deriving.Demo.Foo] = List(Bar, Baz)
}


