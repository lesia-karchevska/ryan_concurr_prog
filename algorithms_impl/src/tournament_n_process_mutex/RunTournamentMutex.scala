package tournament_n_process_mutex

import java.time.LocalDateTime

import scala.util.Random

object RunTournamentMutex extends App {

  @volatile
  var stop: Boolean = false

  def getProcess(i: Int, rnd: Random, mutex: TournamentMutex): Thread = {
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

  val mutex = new TournamentMutex(4)
  val processes = Range(1, 17).map(i => getProcess(i, new Random, mutex))
  processes.foreach(p => p.start)

  Thread.sleep(5000)
  stop = true
  Thread.sleep(1000)
  processes.foreach(p => println(p.getState))
}
