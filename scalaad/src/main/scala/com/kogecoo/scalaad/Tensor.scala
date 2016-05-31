package com.kogecoo.scalaad

import com.kogecoo.scalaad.algorithm.Value
import com.kogecoo.scalaad.impl.std.{StdMat, StdVec}


//trait ComplexScalar { }

trait Tensor[S <: Shape]


trait Tensor0 extends Tensor[S0] {

  def value[V](implicit v: Value[Tensor0, V]): V = v.value(this)

  def shape: S0 = Shape0()

  def toStdFloat:  Float

  def toStdDouble: Double

}


trait Tensor1 extends Tensor[S1] {

  val transposed: Boolean

  def value[V](implicit v: Value[Tensor1, V]): V = v.value(this)

  def shape: S1

  def toStdFloat:  StdVec[Float]

  def toStdDouble: StdVec[Double]

}


trait Tensor2 extends Tensor[S2] {

  def value[V](implicit v: Value[Tensor2, V]): V = v.value(this)

  def shape: S2

  def toStdFloat:  StdMat[Float]

  def toStdDouble: StdMat[Double]

}


trait BooleanTensor0 extends Tensor[S0] {

  def value[V](implicit v: Value[BooleanTensor0, V]): V = v.value(this)

  def shape: S0 = Shape0()

  def toStd: Boolean

}


trait BooleanTensor1 extends Tensor[S1] {

  def value[V](implicit v: Value[BooleanTensor1, V]): V = v.value(this)

  def shape: S1

  def toStd: StdVec[Boolean]

}

trait BooleanTensor2 extends Tensor[S2] {

  def value[V](implicit v: Value[BooleanTensor2, V]): V = v.value(this)

  def shape: S2

  def toStd: StdMat[Boolean]

}

