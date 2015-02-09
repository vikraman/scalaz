package scalaz

import scala.language.experimental.macros
import scala.reflect.runtime.universe._
import scala.reflect.macros.blackbox.Context

object Deriving {
  def mkEnum[A]: Enum[A] = macro derivingEnum[A]

  def derivingEnum[A: c.WeakTypeTag](c: Context): c.Expr[Enum[A]] = {
    import c.universe._
    val symbol = weakTypeOf[A].typeSymbol
    val internal = symbol.asInstanceOf[scala.reflect.internal.Symbols#Symbol]
    if (!internal.isSealed) c.abort(c.enclosingPosition, "This isn't a sealed type.")

    val descendants = internal.sealedDescendants.map(_.asInstanceOf[Symbol])

    val objs = (descendants - symbol).map(
      s => s.owner.typeSignature.member(s.name.toTermName)
    )

    if (objs.isEmpty) c.abort(c.enclosingPosition, "No objects found (please see scaladoc for more information).")


    c.Expr[Enum[A]](
      Apply(
        Select(Select(Ident(TermName("scalaz")), TermName("Enum")), TermName("fromList")),
        List(
          Apply(
            Select(Ident(TermName("List")), TermName("apply")),
            objs.map(Ident(_)).to[List]
          )
        )
      )
    )
  }
}
