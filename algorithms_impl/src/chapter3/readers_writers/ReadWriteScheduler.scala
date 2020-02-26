package chapter3.readers_writers

import java.time.LocalDateTime

import chapter3.common.Semaphore

class ReadWriteScheduler {
  private var NBR = 0;
  private val NBR_MUTEX = new Semaphore(1, 1)
  private val GLOBAL_MUTEX = new Semaphore(1, 1)

  def begin_read() = {
    NBR_MUTEX.down()
    NBR = NBR + 1
    if (NBR == 1) { GLOBAL_MUTEX.down }
    println(println(Thread.currentThread().getName + " started reading file at " + LocalDateTime.now))
    NBR_MUTEX.up
  }

  def end_read() = {
    NBR_MUTEX.down
    NBR = NBR - 1
    if (NBR == 0) { GLOBAL_MUTEX.up }
    println(println(Thread.currentThread().getName + " is finishing reading file at " + LocalDateTime.now))
    NBR_MUTEX.up
  }

  def begin_write() = {
    GLOBAL_MUTEX.down
    println(println(Thread.currentThread().getName + " started writing to file at " + LocalDateTime.now))
  }

  def end_write() = {
    println(println(Thread.currentThread().getName + " finished writing to file at " + LocalDateTime.now))
    GLOBAL_MUTEX.up
  }
}
