package exercises_ch2.ex4

class Mutex4(delay: Int) extends common.Mutex {
  @volatile
  private var x: Int = -1
  @volatile
  private var y: Int = -1
  @volatile
  private var allowed = true

  override def acquire_mutex(i: Int): Unit = {
    def go(): Unit = {
      while (!y.equals(-1)) {}
      y = i
      if (x.equals(i)) {
        allowed = false
      } else {
        Thread.sleep(3 * delay)
        if (!(y.equals(i) && allowed)) go()
      }
    }
    x = i
    go()
  }

  override def release_mutex(i: Int): Unit = {
    allowed = true
    y = -1
  }
}
