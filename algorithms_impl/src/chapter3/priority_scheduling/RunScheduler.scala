package chapter3.priority_scheduling

import scala.util.Random

object RunScheduler extends App {

  @volatile
  var stop = false
  val n = 5

  val scheduler = new Scheduler(n)

  Range(0, n).foreach(i => {
    val rnd = new Random
    new Thread(() => {
      while (!stop) {
        Thread.sleep(rnd.nextInt(200) + 100)
        var p = rnd.nextInt(1000)
        rnd.setSeed(p)
        println("Process " + i + " starts competing for the critical section with priority " + p)
        scheduler.acquire(i, p)
        println("Process " + i + " in the critical section")
        Thread.sleep(rnd.nextInt(100))
        println("Process " + i + " leaves the critical section")
        scheduler.release(i)
      }

    })
      .start()
  })

  Thread.sleep(500000)
  stop = true
}
