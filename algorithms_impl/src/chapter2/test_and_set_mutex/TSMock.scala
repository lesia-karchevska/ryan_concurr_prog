package chapter2.test_and_set_mutex

class TSMock {

  private var x: Int = 1
  private val lock: Object = new AnyRef

  def testAndSet(): Int = {
    lock.synchronized {
      val prev = x
      x = 0
      prev
    }
  }

  def reset() = {
    lock.synchronized {
      x = 1
    }
  }
}
