package exercises_ch2.ex6

import java.time.LocalDateTime

import common.{Mutex, MutexExerciser}

import scala.util.Random

object RunSubsetMutex extends App {

  def testMutex(mutex: SubsetMutex, start: Int, end: Int, timeTillStop: Int = 5000, waitTime: Int = 3000): Unit = {

    @volatile
    var stop: Boolean = false
    def getProcess(i: Int, rnd: Random): Thread = {
      new Thread(() => {
        while (!stop) {
          Thread.sleep(rnd.nextInt(1))
          mutex.acquire_mutex(i)
          println("Process " + i + " acquired mutex at " + LocalDateTime.now().toString)
          Thread.sleep(rnd.nextInt(1))
          println("Process " + i + " is about to release mutex at " + LocalDateTime.now().toString)
          mutex.release_mutex(i)
        }
      })
    }

    println("Note: resetting of the mapping set obviously doesn't take immediate effect after !")
    val processes = Range(start, end).map(i => getProcess(i, new Random))
    new Thread(() => {
      val rnd = new Random()
      while (!stop) {
        Thread.sleep(rnd.nextInt(500))
        val x = rnd.nextInt(end)
        val newS = Range(0, end).map(r => (r + x) % end)
        println("Resetting mapping: x = " + x + ", new order: " + newS)
        mutex.setMapping(newS.toArray)
      }
    }).start()

    processes.foreach(p => p.start)

    Thread.sleep(timeTillStop)
    stop = true
    Thread.sleep(waitTime)
    processes.foreach(p => println(p.getState))
  }

  testMutex(new SubsetMutex(15, 20), 0, 15)
}
