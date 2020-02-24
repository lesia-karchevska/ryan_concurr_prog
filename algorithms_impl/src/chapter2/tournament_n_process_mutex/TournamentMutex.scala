package chapter2.tournament_n_process_mutex

import chapter2.peterson_two_process.Mutex

import scala.annotation.tailrec

class TournamentMutex(power: Int) extends chapter2.common.Mutex {

  private val n = 1 << power
  private val locks = List.fill(n - 1)(new Mutex)
  private val p_ids = List.fill(n)(new Array[Int](power))

  def acquire_mutex(i: Int) = {
    var node_id = i + (n - 1)
    def go(k: Int): Unit = {
      if (k <= power) {
        p_ids(i - 1)(k - 1) = node_id & 1
        node_id = node_id / 2
        locks(node_id - 1).acquire_mutex(p_ids(i - 1)(k - 1))
        go(k + 1)
      }
    }
    go(1)
  }

  def release_mutex(i: Int) = {
    @tailrec
    def go(k: Int, node_id: Int): Unit = {
      if (k > 0) {
        locks(node_id - 1).release_mutex(p_ids(i - 1)(k - 1))
        go(k - 1, 2 * node_id + p_ids(i - 1)(k - 1))
      }
    }
    go(power, 1)
  }
}
