package fischer_mutex

import common.Mutex

class FischerMutex(delay: Int) extends Mutex {

  @volatile
  private var x: Int = -1

  def acquire_mutex(i: Int): Unit = {
    def go(): Unit = {
      while (!x.equals(-1)) {}
      x = i
      Thread.sleep(delay)
      if (!x.equals(i)) go()
    }

    go()
  }

  def release_mutex(i: Int): Unit = {
    x = -1
  }
}
