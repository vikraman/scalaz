package scalaz

object SList {
  type List[A] = Prelude.SList[A]
  val Nil = scala.collection.immutable.Nil
  val :: = scala.collection.immutable.::
}
