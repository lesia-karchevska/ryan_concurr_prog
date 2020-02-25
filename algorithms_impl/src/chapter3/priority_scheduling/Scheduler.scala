package chapter3.priority_scheduling

import chapter3.common.Semaphore

class Scheduler(n: Int) {

  private val MUTEX = new Semaphore(1, 1)
  private var BUSY = false
  private val WAITING = new Array[Boolean](n)
  private val SLEEP_CHAIR = new Array[Semaphore](n)
  private val PRIORITY = new Array[Int](n)

  Range(0, n).foreach(i => {
    SLEEP_CHAIR(i) = new Semaphore(0, 1)
  })

  private def findHighestPriorityWaitingProcess(): Int = {
    Range(0, n).filter(k => WAITING(k).equals(true)).map(j => (j, PRIORITY(j))).maxByOption(x => x._2).map(x => x._1).getOrElse(-1)
  }

  def acquire(i: Int, p: Int) = {
    MUTEX.down()
    if (BUSY) {
      WAITING(i) = true
      PRIORITY(i) = p
      MUTEX.up()
      SLEEP_CHAIR(i).down()
    } else {
      BUSY = true
      MUTEX.up()
    }
  }

  def release(i: Int) = {
    MUTEX.down()
    val proc_id = findHighestPriorityWaitingProcess()
    if (proc_id > -1) {
      WAITING(proc_id) = false
      SLEEP_CHAIR(proc_id).up()
    } else {
      BUSY = false
    }
    MUTEX.up()
  }
}
