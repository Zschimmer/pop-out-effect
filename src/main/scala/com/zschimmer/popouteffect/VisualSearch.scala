package com.zschimmer.popouteffect

import com.zschimmer.popouteffect.VisualSearch._

/**
  * @author Joacim Zschimmer
  */
final class VisualSearch(val width: Int, val height: Int) {

  def newTrial(condition: Condition): Trial = {
    require(condition.totalSize <= size, "Matrix is too small for requested number of stimuli")
    val points = randomlyPermutateNumbers(size).iterator take condition.totalSize map toPoint
    new Trial(
      for {
        k ← Kind.values.toVector
        p ← points take condition.partitionSize(k)
      } yield Stimulus(p, k))
  }

  private def toPoint(n: Int) = Point(n % width, n / width)

  private def size = width * height

  def contains(p: Point) = p.x >= 0 && p.x < width && p.y >= 0 && p.y < height

  final class Trial(val stimuli: Vector[Stimulus]) {
    override def toString = toString(KindToColoredString)

    def toString(kindToString: Kind ⇒ String) = {
      val pointToKind = (stimuli map { o ⇒ o.point → o.kind }).toMap
      (for (y ← 0 until height) yield
        (for (x ← 0 until width) yield
          pointToKind.get(Point(x, y)) map kindToString getOrElse " "
        ).mkString("\n|", "", "|")
      ).mkString
    }
  }
}

object VisualSearch {
  private val DefaultColor = "\u001b[0m"
  private val Green = "\u001b[32m"
  private val Red = "\u001b[31m"
  val StartMessage = s"If you see a red ${Red}o$DefaultColor, press y and Enter, otherwise press n and Enter"

  private val KindToColoredString = Map(
    Kind.InvalidShapeInvalidColor → s"${Green}x$DefaultColor",
    Kind.InvalidShapeValidColor → s"${Red}x$DefaultColor",
    Kind.ValidShapeInvalidColor → s"${Green}o$DefaultColor",
    Kind.Valid → s"${Red}o$DefaultColor")

  val KindToString = Map(
    Kind.InvalidShapeInvalidColor → "x",
    Kind.InvalidShapeValidColor → "X",
    Kind.ValidShapeInvalidColor → "o",
    Kind.Valid → "O")
}
