package com.kogecoo.scalaad.graph

import com.kogecoo.scalaad.Shape


abstract class Where(cond: Expr, l: DExpr, r: DExpr) extends Expr with Differentiable


case class ElementwiseWhere(cond: Expr, l: DExpr, r: DExpr) extends Where(cond, l, r) {

  @throws[Exception]
  final def shape: Shape = BroadcastHelper.computeOutputShape(Seq[Shape](l.shape, r.shape))

  def forward(wrt: DExpr): DExpr = ElementwiseWhere(cond, l.forward(wrt), r.forward(wrt))

  def reverse(adj: DExpr): Grad = {
    val lg: Grad = l.reverse(adj)
    val rg: Grad = r.reverse(adj)
    interpolateToGrad(lg, rg)
  }

  protected[this] def interpolateToGrad(lg: Grad, rg: Grad): Grad = {
    val builder = Map.newBuilder[DExpr, DExpr]

    (lg.m.keySet | rg.m.keySet).foreach { k =>
      (lg.m.get(k), rg.m.get(k)) match {
        case (Some(a), Some(b)) => builder += ((k, ElementwiseWhere(cond, a, b)))
        case (Some(a), None)    => builder += ((k, ElementwiseWhere(cond, a, Zero(a.shape))))
        case (None,    Some(b)) => builder += ((k, ElementwiseWhere(cond, Zero(b.shape), b)))
        case (None,    None)    => ()
      }
    }
    new Grad(builder.result())
  }

}

