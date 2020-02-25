package chapter3.multiple_consumer_producer

import scala.util.Random

object RunMultiConsumerProducer extends App {

  @volatile
  var stop = false
  val cp = new MultConsumerProducer[Int](30, new Array[Int](30))

  Range(0, 15).foreach(i => {
    val rnd = new Random()
    new Thread(() => {
      while (!stop) {
        Thread.sleep(rnd.nextInt(50))
        println("Producer " + i + " waiting to write")
        cp.produce(() => { Thread.sleep(rnd.nextInt(50)); i })
        println("Producer " + i + " executed")
      }
    }).start()
    new Thread(() => {
      while (!stop) {
        Thread.sleep(rnd.nextInt(100))
        println("Consumer " + i + " waiting to read")
        println("Consumer " + i + " consumed " + cp.consume)
      }
    }).start()
  })

  Thread.sleep(5000)
  stop = true
}
