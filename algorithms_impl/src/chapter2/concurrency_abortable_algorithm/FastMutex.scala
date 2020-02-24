package chapter2.concurrency_abortable_algorithm

import chapter2.common.Mutex
import chapter2.util.VolatileIntArray

class FastMutex(n: Int) extends Mutex {

  private val flag = new VolatileIntArray(n)
  @volatile
  private var x: Int = -1
  @volatile
  private var y: Int = -1

  private def waitForDown(): Int = {
    def go(curr: Int, currNum: Int): Int = {
      if (currNum < n) go(curr | flag.get(currNum), currNum + 1)
      else curr
    }
    go(0, 0)
  }

  def acquire_mutex(i: Int) = {
    flag.update(i, 1)
    x = i
    if (!y.equals(-1)) {
      flag.update(i, 0)
      while (!y.equals(-1)) {}
      acquire_mutex(i)
    } else {
      y = i
      if (!x.equals(i)) {
        flag.update(i, 0)
        while (waitForDown > 0) {}
        if (!y.equals(i)) {
          while (!y.equals(-1)) {}
          acquire_mutex(i)
        }
      }
    }
  }

  def release_mutex(i: Int) = {
    y = -1
    flag.update(i, 0)
  }
}
