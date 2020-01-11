package two_process_generalised_for_n

import java.time.LocalDateTime

import scala.util.Random

object RunNProcessMutex extends App {

  @volatile
  var stop: Boolean = false

  def getProcess(i: Int, rnd: Random, mutex: Mutex): Thread = {
    new Thread(() => {
      while (!stop) {
        Thread.sleep(rnd.nextInt(100))
        mutex.acquire_mutex(i)
        println("Process " + i + " acquired mutex at " + LocalDateTime.now().toString)
        Thread.sleep(rnd.nextInt(100))
        println("Process " + i + " is about to release mutex at " + LocalDateTime.now().toString)
        mutex.release_mutex(i)
      }
    })
  }

  val mutex = new Mutex(15)
  val processes = Range(0, 15).map(i => getProcess(i, new Random, mutex))
  processes.foreach(p => p.start)

  Thread.sleep(10000)
  stop = true
  Thread.sleep(1000)
  processes.foreach(p => println(p.getState))
}
