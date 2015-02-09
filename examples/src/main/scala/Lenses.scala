package acme

import scalaz._

import Optic._

package object lenses {
  case class User(_name: String, _age: Int)
  object User {
    val name = slens[User, String](_._name)(u => n => u.copy(_name = n))
    val age  = slens[User, Int](_._age)(u => a => u.copy(_age = a))
  }

  case class Document(_title: String, _author: User, _reviewedBy: Maybe[User])
  object Document {
    val title       = slens[Document, String](_._title)(u => n => u.copy(_title = n))
    val author      = slens[Document, User](_._author)(u => a => u.copy(_author = a))
    val reviewedBy  = slens[Document, Maybe[User]](_._reviewedBy)(u => a => u.copy(_reviewedBy = a))
  }

  import User._
  import Document._

  val authorName = author ∘ name
  val reviewerAge = reviewedBy ∘ _just[User] ∘ age
}


