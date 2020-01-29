package fetch_and_add_mutex

class FAMock {

  private var i: Int = 0
  private val lock = new AnyRef

  def fetchAndAdd(): Int = {
    lock.synchronized {
      i = i + 1
      i
    }
  }
}
