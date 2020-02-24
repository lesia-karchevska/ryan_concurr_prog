package chapter2.starvation_free_on_top_of_deadlock_free

import chapter2.common.Mutex
import chapter2.concurrency_abortable_algorithm.FastMutex
import chapter2.util.VolatileIntArray

class SFMutex(n: Int) extends Mutex {

  private val lock = new FastMutex(n)
  private val flag = new VolatileIntArray(n)
  @volatile
  private var turn: Int = 0

  override def acquire_mutex(i: Int): Unit = {
    flag.update(i, 1)
    while (!(turn.equals(i) || flag.get(turn).equals(0))) {}
    lock.acquire_mutex(i)
  }

  override def release_mutex(i: Int): Unit = {
    flag.update(i, 0)
    if (flag.get(turn).equals(0)) {
      turn = turn % n
    }
    lock.release_mutex(i)
  }
}
