package com.zschimmer.popouteffect

import com.zschimmer.popouteffect.Runner._
import java.lang.System.nanoTime
import java.time.Duration
import scala.annotation.tailrec

/**
  * @author Joacim Zschimmer
  */
final class Runner(nextPressedYesNo: (Condition) ⇒ Boolean) {

  private val visualSearch = new VisualSearch(10, 5)

  def run(n: Int): Vector[Measurement] =
    for {
      popOut ← Vector(false, true)
      size ← Sizes
      hasTarget ← randomlyPermutate(Vector.fill(n)(false) ++ Vector.fill(n)(true))
    } yield {
      val condition = Condition(hasTarget = hasTarget, popOut = popOut, n = size)
      Measurement(condition, runTrial(condition))
    }

  @tailrec
  private def runTrial(condition: Condition): Duration = {
    val trial = visualSearch.newTrial(condition)
    println(trial)
    val t = nanoTime
    val ok = nextPressedYesNo(condition) == condition.hasTarget
    val duration = Duration.ofNanos(nanoTime - t)
    println(if (ok) "GOOD" else "BAD")
    if (ok) duration else runTrial(condition)
  }
}

object Runner {
  val SameConditionCount = 10
  val Sizes = 1 to 4

  def main(args: Array[String]): Unit = {
    println(VisualSearch.StartMessage)
    System.in.read()  // Simplification: empty input expected
    val measurements = new Runner(readPressedYesNo).run(SameConditionCount)
    println(new Graphic(Sizes.last - 1, 10, measurements).toAllResultGraphics(measurements))
  }

  @tailrec
  private def readPressedYesNo(condition: Condition): Boolean =
    System.in.read() match {
      case -1 ⇒ sys.error("EOF")
      case 'y' ⇒ true
      case 'n' ⇒ false
      case _ ⇒ readPressedYesNo(condition)
    }
}
