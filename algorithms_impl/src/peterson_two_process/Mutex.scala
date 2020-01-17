package peterson_two_process

import util.VolatileIntArray

//Simple mutex based on Peterson Two Process algorithm
class Mutex extends common.Mutex{

  @volatile
  private var after_you: Int = 0
  private val flag = new VolatileIntArray(2)

  def acquire_mutex(i: Int) = {
    flag.update(i, 1)
    after_you = i
    //this is of course inefficient
    while (!flag.get(i^1).equals(0) && after_you.equals(i)) {}
  }

  def release_mutex(i: Int) = {
    flag.update(i, 0)
  }

}
