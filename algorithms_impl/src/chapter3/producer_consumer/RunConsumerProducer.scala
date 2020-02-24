package chapter3.producer_consumer

import scala.util.Random

object RunConsumerProducer extends App {

  val busy = new Semaphore(0, 20)
  val free = new Semaphore(20, 20)
  val buffer = new Array[Int](20)
  val consumer = new Consumer(busy, free, buffer)
  val producer = new Producer(busy, free, buffer)
  @volatile
  var stop = false
  new Thread(() => {
    val rnd = new Random
    while (!stop) {
      producer.produce(rnd.nextInt(23))
    }
  }).start
  new Thread(() => {
    while (!stop) {
      consumer.consume()
    }
  }).start

  Thread.sleep(5000)
  stop = true
}
