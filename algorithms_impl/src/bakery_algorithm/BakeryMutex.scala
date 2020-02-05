package bakery_algorithm

import common.{Mutex, SafeRegister}

class BakeryMutex(n: Int) extends Mutex {

  private val flag = List.fill(n)(new SafeRegister[Boolean](false))
  private val my_turn = List.fill(n)(new SafeRegister[Int](0))

  private def order(turn_i: Int, i: Int, turn_j: Int, j: Int): Boolean = {
    if (turn_i < turn_j) return true
    if (turn_i.equals(turn_j) && i < j) return true
    false
  }

  def acquire_mutex(i: Int): Unit = {
    flag(i).write(true)
    val turn = my_turn.map(sr => sr.read()).max + 1
    my_turn(i).write(turn)
    println("process " + i + " turn is " + turn)
    flag(i).write(false)
    Range(0, n).filter(j => !j.equals(i)).foreach(j => {
      while (!flag(j).read().equals(false)) {}
     // println("process " + i + " bypassed process " + j + " on flag")
      var turn_j = my_turn(j).read()
      while (!turn_j.equals(0) && !order(turn, i, turn_j, j)) { turn_j = my_turn(j).read() }
     // println("process " + i + " bypassed process " + j)
    })
  }

  def release_mutex(i: Int): Unit = {
    my_turn(i).write(0)
  }
}
