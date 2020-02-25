package chapter3.multiple_consumer_producer

import chapter3.common.Semaphore
import util.VolatileIntArray

class MultConsumerProducer[T](n: Int, BUFFER: Array[T]) {

  @volatile
  private var in: Int = 0
  @volatile
  private var out: Int = 0

  private val EMPTY = new VolatileIntArray(n)
  private val FULL = new VolatileIntArray(n)

  Range(0, n).foreach(i => {
    EMPTY.update(i, 1)
    FULL.update(i, 0)
  })

  private val FREE = new Semaphore(n, n)
  private val BUSY = new Semaphore(0, n)

  private val MC = new Semaphore(1, 1)
  private val MP = new Semaphore(1, 1)

  def produce(v: () => T) = {
    FREE.down
    MP.down
    //println("producer looking for index")
    while (EMPTY.get(in).equals(0)) in = (in + 1) % n
    val curr_index = in
    EMPTY.update(in, 0)
    in = (in + 1) % n
    MP.up
    //println("producing")
    BUFFER(curr_index) = v.apply()
    FULL.update(curr_index, 1)
    BUSY.up
  }

  def consume(): T = {
    BUSY.down
    MC.down
 //   println("consumer looking for index")
    while (FULL.get(out).equals(0)) out = (out + 1) % n
    val curr_index = out
    FULL.update(out, 0)
    out = (out + 1) % n
    MC.up
    val res = BUFFER(curr_index)
 //   println("consumer consumed")
    EMPTY.update(curr_index, 1)
    FREE.up
    res
  }
}
