package com.kogecoo.scalaad.op

import com.kogecoo.scalaad.{Shape, Tensor}
import shapeless.Nat
import shapeless.Nat._1


// Apply0(NullaryOp) is redundant expression of constant Expr.
//
// For example, Apply0[N](ZeroOp) is equivalent to Zero[N]

case object ZeroOp extends NullaryOp

case object OneOp  extends NullaryOp

case object HalfOp extends NullaryOp

case class ConstOp[N <: Nat](v: Tensor[N]) extends NullaryOp

case object EyeOp extends NullaryOp

case class DiagOp[N <: Nat](diag: Tensor[_1]) extends NullaryOp
