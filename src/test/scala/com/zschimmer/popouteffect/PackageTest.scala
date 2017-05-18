package com.zschimmer.popouteffect

import org.scalatest.FreeSpec

/**
  * @author Joacim Zschimmer
  */
final class PackageTest extends FreeSpec {

  "Condition" - {
    "0" in {
      check(Condition(hasTarget = false, popOut = false, n = 3), 3, 3, 6, 0)
    }

    "hasTarget" in {
      check(Condition(hasTarget = true, popOut = false, n = 3), 3, 3, 5, 1)
    }

    "popOut" in {
      check(Condition(hasTarget = false, popOut = true, n = 3), 6, 0, 6, 0)
    }

    "popOut && hasTarget" in {
      check(Condition(hasTarget = true, popOut = true, n = 3), 6, 0, 5, 1)
    }

    def check(condition: Condition, invalidShapeInvalidColor: Int, invalidShapeValidColor: Int, validShapeInvalidColor: Int, valid: Int): Unit = {
      assert(condition.partitionSize(Kind.InvalidShapeInvalidColor) == invalidShapeInvalidColor)
      assert(condition.partitionSize(Kind.InvalidShapeValidColor) == invalidShapeValidColor)
      assert(condition.partitionSize(Kind.ValidShapeInvalidColor) == validShapeInvalidColor)
      assert(condition.partitionSize(Kind.Valid) == valid)
      assert(condition.totalSize == 4 * condition.n)
      assert((Kind.values.iterator map condition.partitionSize).sum == condition.totalSize)
    }
  }
}
