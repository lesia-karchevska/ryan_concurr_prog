package chapter2.exercises_ch2.ex6

import chapter2.common.SafeRegister

class SubsetMutex(n: Int, BOUND: Int) extends chapter2.common.Mutex {
  private val stage = List.fill(n)(new SafeRegister[Boolean](false))
  private val date =  List.fill(n)(new SafeRegister[Int](0))
  private val flag = List.fill(n)(new SafeRegister[Boolean](false))

  @volatile
  //this makes the processes mapped by first BOUND values of the array not starve whenever they are competing,
  //the other processes are not guaranteed to not starve
  private var mapping = new Array[Int](n)

  Range(0, n).foreach(i => date(i).write(i + 1))
  Range(0, n).foreach(i => mapping(i) = i)

  def setMapping(newMapping: Array[Int]) = {
    mapping = newMapping
  }

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
    val newDate = date.map(x => x.read).max + 1
    if (newDate > BOUND) {
      val currMapping = mapping
      Range(0, n).foreach(i => date(currMapping(i)).write(i))
    } else {
      date(i).write(newDate)
    }
    stage(i).write(false)
    flag(i).write(false)
  }
}
