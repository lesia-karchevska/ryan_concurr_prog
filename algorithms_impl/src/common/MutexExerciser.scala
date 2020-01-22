package common

import java.time.LocalDateTime

import scala.util.Random

object MutexExerciser {


  def testMutex(getMutex: () => Mutex, start: Int, end: Int, timeTillStop: Int = 5000, waitTime: Int = 3000): Unit = {

    val mutex = getMutex.apply()
    @volatile
    var stop: Boolean = false
    def getProcess(i: Int, rnd: Random): Thread = {
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

    val processes = Range(start, end).map(i => getProcess(i, new Random))
    processes.foreach(p => p.start)

    Thread.sleep(timeTillStop)
    stop = true
    Thread.sleep(waitTime)
    processes.foreach(p => println(p.getState))
  }
}
