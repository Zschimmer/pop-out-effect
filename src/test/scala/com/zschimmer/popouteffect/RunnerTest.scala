package com.zschimmer.popouteffect

import org.scalatest.FreeSpec

/**
  * @author Joacim Zschimmer
  */
final class RunnerTest extends FreeSpec {

  "Runnner" in {
    def readNextYesNo(condition: Condition) = {
      println(condition)
      Thread.sleep((if (condition.hasTarget) 2 else 5) + condition.n * 2)
      condition.hasTarget
    }
    val measurements = new Runner(readNextYesNo).run(Runner.SameConditionCount)
    println(new Graphic(Runner.Sizes.last + 1, 10, measurements).toAllResultGraphics(measurements))
  }
}
