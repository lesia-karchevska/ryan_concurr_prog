package chapter2.exercises_ch2.ex2

class TournamentMutexDeadlockFree {

  /*
  TournamentMutex algorithm:

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

Assume that there is a set Q of processes that invoked acquire_mutex method. Let us prove that for any k > 0
there exists a moment of time t_k such that at that moment of time there exists a process that is at level k - 1  ,
k <= log_2(n) (assertion (*)). Indeed, assume that there is k > 0 such that all processes from Q are forever at levels >=k.
Additionally, we can assume that there is at least one process p* at level k (otherwise we'd increase k).
The process p* can compete with another process p** at level k for deadlock-free lock lock*. Hence one of p*, p** wins
and proceeds to k - 1-th level, a contradiction.

Assertion (*) yields the deadlock-freedom, since it follows that there is a moment of time t_0 at which at least one process
competes for the lock at 0-th level. If there are two such processes, one of them wins, because the underlying lock
is deadlock-free.

   */

}
