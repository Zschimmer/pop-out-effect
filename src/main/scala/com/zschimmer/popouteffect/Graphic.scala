package com.zschimmer.popouteffect

import scala.collection.immutable.Iterable

/**
  * @author Joacim Zschimmer
  */
final class Graphic(width: Int, height: Int, measurements: Iterable[Measurement]) {

  private val maxElementCount = (measurements map { _.condition.n }).max
  private val maxDuration = (measurements map { _.duration }).max

  def toAllResultGraphics(measurements: Iterable[Measurement]): String =
    (for {
      (hasTarget, ms) ← measurements groupBy { _.condition.hasTarget }
      (popOut, ms) ← ms groupBy { _.condition.popOut }
    } yield
      s"\n\nTime per quantity with hasTarget=$hasTarget popOut=$popOut\n" + toResultGraphic(ms))
    .mkString

  def toResultGraphic(measurements: Iterable[Measurement]): String = {
    val canvas = Vector.fill(height) { Array.fill(width)(' ') }
    for (m ← measurements) {
      val x = m.condition.n * (width - 1) / maxElementCount
      val y = ((height - 1) * m.duration.toNanos / maxDuration.toNanos).toInt
      canvas(y)(x) = canvas(y)(x) match {
        case ' ' ⇒ '1'
        case '9' | '*' ⇒ '*'
        case o if o.isDigit ⇒ (o + 1).toChar
      }
    }
    (for ((o, i) ← canvas.zipWithIndex.reverseIterator) yield
      o.mkString + s"| ${i * maxDuration.toMillis / height}ms")
    .mkString("\n|", "\n|", "|")
  }
}
