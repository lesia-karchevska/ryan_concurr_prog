package chapter3.readers_writers

import java.time.LocalDateTime

import scala.util.Random

object RunReadWriteScheduler extends App {

  @volatile
  var stop = false
  val scheduler = new ReadWriteScheduler
  Range(0, 20).foreach(i => {
    new Thread(() => {
      val rnd = new Random()
      Thread.currentThread.setName("Reader " + i)
      while (!stop) {
        Thread.sleep(rnd.nextInt(300))
        scheduler.begin_read()
        println("Reader " + i + " is reading file at " + LocalDateTime.now)
        scheduler.end_read()
      }
    }).start
  })

  Range(0, 20).foreach(i => {
    new Thread(() => {
      val rnd = new Random()
      Thread.currentThread.setName("Writer " + i)
      while (!stop) {
        Thread.sleep(rnd.nextInt(100))
        scheduler.begin_write()
        println("Writer " + i + " is writing to file at " + LocalDateTime.now)
        scheduler.end_write()
      }
    }).start
  })

  Thread.sleep(7000)
  stop = true
}
