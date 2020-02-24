package chapter2.util

import scala.annotation.tailrec

object MathUtils {

  def log2NCeiling(n: Integer): Integer = {
    @tailrec
    def go(k: Int): Int = {
      if (Math.pow(2, k) >= n) k
      else go(k + 1)
    }

    go(0)
  }
}
