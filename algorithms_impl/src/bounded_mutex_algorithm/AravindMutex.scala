package bounded_mutex_algorithm

import common.{Mutex, SafeRegister}

class AravindMutex(n: Int) extends Mutex {

  private val stage = List.fill(n)(new SafeRegister[Boolean](false))
  private val date =  List.fill(n)(new SafeRegister[Int](0))
  private val flag = List.fill(n)(new SafeRegister[Boolean](false))

  Range(0, n).foreach(i => date(i).write(i + 1))

  override def acquire_mutex(i: Int): Unit = {
    flag(i).write(true)
    def go_predicate(): Boolean = {
      Range(0, n)
        .filter(j => !j.equals(i))
        .map(j => (flag(j).read, date(j).read))
        .foldLeft(true)((x, y) => x && (y._1.equals(false) || date(i).read < y._2))
    }

    def go(): Unit = {
      stage(i).write(false)
      while (!go_predicate) {}
      stage(i).write(true)
      val terminate = Range(0, n)
      .filter(j => !j.equals(i))
        .foldLeft(true)((x, j) => x && stage(j).read.equals(false))
      if (!terminate) go()
    }
    go()
  }

  override def release_mutex(i: Int): Unit = {
    date(i).write(date.map(x => x.read).max + 1)
    stage(i).write(false)
    flag(i).write(false)
  }
}
