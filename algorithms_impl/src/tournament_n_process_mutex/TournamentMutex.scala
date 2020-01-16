package tournament_n_process_mutex

import peterson_two_process.Mutex
import util.MathUtils

import scala.annotation.tailrec

class TournamentMutex(power: Int) {

  private val n = 1 << power
  private val locks = List.fill(n)(new Mutex)
  private val p_ids = List.fill(n + 1)(new Array[Int](power + 1))

  def acquire_mutex(i: Int) = {
    var node_id = i + (n - 1)
    def go(k: Int): Unit = {
      if (k <= power) {
        p_ids(i)(k) = node_id & 1
        node_id = node_id / 2
        locks(node_id).acquire_mutex(p_ids(i)(k))
        go(k + 1)
      }
    }

    go(1)
  }

  def release_mutex(i: Int) = {
    @tailrec
    def go(k: Int, node_id: Int): Unit = {
      if (k > 0) {
        locks(node_id).release_mutex(p_ids(i)(k))
        go(k - 1, 2 * node_id + p_ids(i)(k))
      }
    }
    go(power, 1)
  }
}
