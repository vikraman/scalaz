package acme

import scalaz._
import Lens._

package object lenses {
  case class User(_name: String, _age: Int)
  object User {
    val name = slens[User, String](_._name)(u => n => u.copy(_name = n))
    val age  = slens[User, Int](_._age)(u => a => u.copy(_age = a))
  }

  case class Document(_title: String, _author: User)
  object Document {
    val title   = slens[Document, String](_._title)(u => n => u.copy(_title = n))
    val author  = slens[Document, User](_._author)(u => a => u.copy(_author = a))
  }

  import User._
  import Document._

  val foo = author ∘ name


}


