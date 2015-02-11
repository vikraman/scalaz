package acme
package deriving

import scalaz._
import system.IO._

object Demo extends App {
  sealed trait Foo
  case object Bar extends Foo
  case object Baz extends Foo

  implicit val enum: Enum[Foo] = Deriving.mkEnum[Foo]

  // scala> enum.all
  // res0: List[Foo] = List(Bar, Baz)

  case class Person(name: String, age: Int)
  object Person { implicit val show: Show[Person] = Deriving.mkShow[Person] }

  case class Cons(head: String, tail: Option[Cons])
  object Cons { implicit def show: Show[Cons] = Deriving.mkShow[Cons] }

  sealed trait Tree
  case class Leaf(name: String) extends Tree
  case class Node(left: Tree, right: Tree) extends Tree

  object Tree { implicit def show: Show[Tree] = Deriving.mkShow[Tree] }

  def mainIO: IO[Unit] =
    print(Person("Alice", 17)) *>
    print(Cons("auto", Some(Cons("deriving", None)))) *>
    print(Node(Leaf("auto"), Leaf("deriving")): Tree)

  // scala> main(Array())
  // Person(Alice, 17)
  // Cons(auto, Some(Cons(deriving, None))
  // Tree(Tree(auto), Tree(deriving))
}


