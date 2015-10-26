package com.kogecoo.scalaad.test.graph.op.spec

import com.kogecoo.scalaad.graph.{Node, Neg}
import com.kogecoo.scalaad.rule._
import com.kogecoo.scalaad.test.helper.gen._
import com.kogecoo.scalaad.test.helper.rule.SeqFloatSoftCompareRule
import com.kogecoo.scalaad.test.helper.rule.SeqFloatValueRule.Implicits._
import com.kogecoo.scalaad.test.helper.specgen.{UnaryOpSpec, UnaryOpExpectedBehaviorDef}
import org.scalacheck.Properties

import scala.language.higherKinds


object NegSpecSeqFloat extends Properties("Neg - Seq[Float]") {

  implicit val compareRule = new SeqFloatSoftCompareRule

  val nodeGen  = new SeqFloatNodeGen
  val valueGen = new SeqFloatValueGen
  val expects  = new NegSeqFloatExpectedBehavior

  val seqFloatSpecGen = new UnaryOpSpec[Seq, Float](expects, nodeGen, valueGen)

  property("-a apply") = seqFloatSpecGen.applyScalar()
  property("-a apply") = seqFloatSpecGen.applyContainer()
  property("-a apply") = seqFloatSpecGen.applyVar()

  property("-a (scalar) w.r.t. a")    = seqFloatSpecGen.derivScalarWrtSelf()
  property("-a (scalar) w.r.t. b")    = seqFloatSpecGen.derivScalarWrtUnknown()
  property("-a (container) w.r.t. a") = seqFloatSpecGen.derivContainerWrtSelf()
  property("-a (container) w.r.t. b") = seqFloatSpecGen.derivContainerWrtUnknown()
  property("-a (var) w.r.t. a")       = seqFloatSpecGen.derivContainerWrtSelf()
  property("-a (var) w.r.t. b")       = seqFloatSpecGen.derivContainerWrtUnknown()

  property("-a (scalar) propagete with value")        = seqFloatSpecGen.propagateScalarWithNCValue()
  property("-a (scalar) propagate with container")    = seqFloatSpecGen.propagateScalarWithCValue()
  property("-a (container) propagete with value")     = seqFloatSpecGen.propagateContainerWithNCValue()
  property("-a (container) propagate with container") = seqFloatSpecGen.propagateContainerWithCValue()
  property("-a (var) propagete with value")           = seqFloatSpecGen.propagateVarWithNCValue()
  property("-a (var) propagate with container")       = seqFloatSpecGen.propagateVarWithCValue()

  property("-a grad")              = seqFloatSpecGen.gradScalar()
  property("-a grad")              = seqFloatSpecGen.gradContainer()
  property("-a grad")              = seqFloatSpecGen.gradVar()

}


class NegSeqFloatExpectedBehavior(implicit vr: ValueRule[Seq, Float]) extends UnaryOpExpectedBehaviorDef[Seq, Float] {

  override val zero: Float = 0f

  override def zero(shape: Seq[Float]): Seq[Float] = Seq.fill(shape.size)(0f)

  override def op(node: Node[Seq, Float]): Node[Seq, Float] = Neg(node)

  override def applyScalar(a: Float): Float              = -a
  override def applyContainer(a: Seq[Float]): Seq[Float] = a.map(-_)
  override def applyVar(a: Seq[Float]): Seq[Float]       = a.map(-_)

  override def derivVarWrtSelf(a: Seq[Float]): Seq[Float] = Seq.fill(a.size)(-1f)

  override def propagateVarWithNCValue(a: Seq[Float], b: Float): Seq[Float]     = Seq.fill(a.size)(-1f * b)
  override def propagateVarWithCValue(a: Seq[Float], b: Seq[Float]): Seq[Float] = a.zip(b).map { case (_, y) => -y }

  override def gradVar(a: Seq[Float]): Seq[Float] = Seq.fill(a.size)(-1f)

}