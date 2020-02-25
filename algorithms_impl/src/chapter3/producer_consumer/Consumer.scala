package chapter3.producer_consumer

import chapter3.common.Semaphore

class Consumer[T](busy: Semaphore, free: Semaphore, buffer: Array[T]) {

  private var in = 0;

  def consume(): T = {
    busy.down
    println("consumer busy down")
    val x = buffer(in)
    in = (in + 1) % buffer.size
    println("consumer consumed " + x)
    free.up
    println("consumer free up")
    x
  }
}
