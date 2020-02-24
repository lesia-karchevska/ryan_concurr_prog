package chapter2.two_process_generalised_for_n

import chapter2.util.VolatileIntArray

class Mutex(n: Int) extends chapter2.common.Mutex {

  //n flags for n processes
  private val flag_level = new VolatileIntArray(n)
  //after_you values at each level - there are n - 1 levels
  private val after_you = new VolatileIntArray(n - 1)

  def acquire_mutex(i: Int) = {

    def allowed(i: Int, l: Int): Boolean = {
      def go(k: Int, res: Boolean): Boolean = {
        if (k < n) {
          if (!k.equals(i)) {
            go(k + 1, res && (flag_level.get(k) < l))
          } else {
            go(k + 1, res)
          }
        } else {
          //after_you numbering starts from 0 i.e. is shifted by 1
          res || !after_you.get(l - 1).equals(i)
        }
      }
      go(0, true)
    }

    def go(l: Int): Unit = {
      flag_level.update(i, l)
      after_you.update(l - 1, i)
      while (!allowed(i, l)) {}
      if (l < n - 1) go(l + 1)
    }
    go(1)
  }

  def release_mutex(i: Int) = {
    flag_level.update(i, 0)
  }
}
