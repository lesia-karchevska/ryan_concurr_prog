package chapter3.readers_writers_buf

import java.time.LocalDateTime

import scala.util.Random

object RWBufSchedulerRunner extends App {

  @volatile
  var stop = false
  val scheduler = new RWBufScheduler
  Range(0, 20).foreach(i => {
    new Thread(() => {
      val rnd = new Random()
      Thread.currentThread.setName("Reader " + i)
      while (!stop) {
        Thread.sleep(rnd.nextInt(300))
        println("Reader " + i + " is reading file at " + LocalDateTime.now + ", got: " + scheduler.read_file())
      }
    }).start
  })

  Range(0, 20).foreach(i => {
    new Thread(() => {
      val rnd = new Random()
      Thread.currentThread.setName("Writer " + i)
      while (!stop) {
        Thread.sleep(rnd.nextInt(100))
        val v = LocalDateTime.now.toString + i.toString
        scheduler.write_file(v)
        println("Writer " + i + " is writing to file at " + LocalDateTime.now + ", wrote: " + v)
      }
    }).start
  })

  Thread.sleep(7000)
  stop = true

}
