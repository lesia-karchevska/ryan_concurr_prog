package peterson_two_process

import java.time.LocalDateTime

import scala.util.Random

object RunTwoProcessMutex extends App {

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

  val mutex = new Mutex
  val process_1 = getProcess(0, new Random, mutex)
  val process_2 = getProcess(1, new Random, mutex)

  process_1.start()
  process_2.start()

  Thread.sleep(10000)
  stop = true
}
