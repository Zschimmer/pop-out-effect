package com.zschimmer

import java.time.Duration
import scala.util.Random

/**
  * @author Joacim Zschimmer
  */
package object popouteffect {

  final case class Condition(hasTarget: Boolean, popOut: Boolean, n: Int) {
    def partitionSize(element: Kind): Int =
      element match {
        case Kind.InvalidShapeInvalidColor ⇒ 2 * n - partitionSize(Kind.InvalidShapeValidColor)
        case Kind.InvalidShapeValidColor ⇒ if (popOut) 0 else n
        case Kind.ValidShapeInvalidColor ⇒ 2 * n - partitionSize(Kind.Valid)
        case Kind.Valid ⇒ if (hasTarget) 1 else 0
      }

    /**
      * Same as sum of partitionSize of all Kind values.
      * Same as (Kind.values map partitionSize).sum
      */
    def totalSize: Int =
      4 * n
  }

  final case class Stimulus(point: Point, kind: Kind)

  final case class Point(x: Int, y: Int)

  type Kind = Kind.Value

  object Kind extends Enumeration {
    val InvalidShapeInvalidColor, InvalidShapeValidColor, ValidShapeInvalidColor, Valid = Value
  }

  final case class Measurement(condition: Condition, duration: Duration)

  def randomlyPermutate[A](xs: IndexedSeq[A]): Vector[A] =
    (randomlyPermutateNumbers(xs.size).iterator map xs).toVector

  def randomlyPermutateNumbers(n: Int): Vector[Int] = {
    val a = (0 until n).toArray
    for (i ← 0 until n - 1) {
      val j = Random.nextInt(i + 1)
      val swap = a(i)
      a(i) = a(j)
      a(j) = swap
    }
    a.toVector
  }
}
