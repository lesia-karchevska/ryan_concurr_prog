package common

class SafeRegister[T](default: T) {

  private var value: T = default
  private val lock = new AnyRef

  def read(): T = {
    value
  }

  def write(v: T): Unit = {
    lock.synchronized {
      value = v
    }
  }
}
