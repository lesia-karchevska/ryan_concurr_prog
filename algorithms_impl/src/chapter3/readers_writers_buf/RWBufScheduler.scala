package chapter3.readers_writers_buf

import chapter3.readers_writers.ReadWriteScheduler

class RWBufScheduler {

  @volatile
  private var LAST: Int = 0

  private val BUF = new Array[String](2)
  private val RW_LOCK = List.fill(2)(new ReadWriteScheduler)

  def read_file() = {
    var value: String = null
    val index = LAST
    RW_LOCK(index).begin_read()
      value = BUF(index)
    RW_LOCK(index).end_read()
    value
  }

  def write_file(v: String) = {
    val new_last = (LAST + 1) % 1
    RW_LOCK(new_last).begin_write()
      BUF(new_last) = v
    RW_LOCK(new_last).end_write()
    LAST = new_last
  }

}
