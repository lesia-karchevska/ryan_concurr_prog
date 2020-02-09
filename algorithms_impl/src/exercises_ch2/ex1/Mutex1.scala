package exercises_ch2.ex1

import common.Mutex
import util.VolatileIntArray

class Mutex1 extends Mutex {

  private val flag = new VolatileIntArray(2)
  private val turn = new VolatileIntArray(2)

  override def acquire_mutex(i: Int): Unit = {
    flag.update(i, 1)
    if (i.equals(0)) {
      turn.update(i, turn.get(i^1)^1)
      while (flag.get(i^1).equals(1) && !turn.get(1).equals(turn.get(0))) {}
    }
    if (i.equals(1)) {
      turn.update(i, turn.get(i^1))
      while (flag.get(i^1).equals(1) && turn.get(1).equals(turn.get(0))) {}
    }
  }

  override def release_mutex(i: Int): Unit = {
    flag.update(i, 0)
  }
}
