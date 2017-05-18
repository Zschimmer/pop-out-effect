package com.zschimmer.popouteffect

import org.scalatest.FreeSpec

/**
  * @author Joacim Zschimmer
  */
final class VisualSearchTest extends FreeSpec {

  "Trial" in {
    val visualSearch = new VisualSearch(10, 4)
    val trail = new visualSearch.Trial(Vector(
      Stimulus(Point(0, 0), Kind.InvalidShapeInvalidColor),
      Stimulus(Point(2, 1), Kind.InvalidShapeValidColor),
      Stimulus(Point(3, 3), Kind.ValidShapeInvalidColor),
      Stimulus(Point(9, 3), Kind.Valid)))
    assert(trail.toString(VisualSearch.KindToString) == """
      ||x         |
      ||  X       |
      ||          |
      ||   o     O|""".stripMargin)
  }

  "VisualSearch" in {
    val visualSearch = new VisualSearch(10, 4)
    val condition = Condition(hasTarget = false, popOut = false, n = 3)
    for (_ ← 1 to 1000) {
      val trial = visualSearch.newTrial(condition)
      assert(trial.stimuli.size == condition.totalSize)
      assert((trial.stimuli map { _.point }).distinct.size == condition.totalSize)
      for (s ← trial.stimuli) assert(visualSearch contains s.point)
    }
  }
}
