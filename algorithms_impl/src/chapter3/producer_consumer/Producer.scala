package chapter3.producer_consumer

import chapter3.common.Semaphore

class Producer[T](busy: Semaphore, free: Semaphore, buffer: Array[T]) {

  private var out = 0;

  def produce(x: T) = {
    free.down
    println("producer free down")
    buffer(out) = x
    out = (out + 1) % buffer.size
    println("Producer produced " + x)
    busy.up
    println("producer busy up")
  }
}
