package fischer_mutex

import common.Mutex

//assumption: two consecutive accesses to atomic registers by a process are separated by at most 'delay' time units
// (if they are not separated by Thread.sleep(delay) call)

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
