package chapter2.exercises_ch2.ex1

import chapter2.common.Mutex
import util.VolatileIntArray

class Mutex1 extends Mutex {

  private val flag = new VolatileIntArray(2)
  private val turn = new VolatileIntArray(2)

  override def acquire_mutex(i: Int): Unit = {
    flag.update(i, 1) //(1)
    if (i.equals(0)) {
      turn.update(i, turn.get(i^1)^1) //(2)
      while (flag.get(i^1).equals(1) && !turn.get(1).equals(turn.get(0))) {} //(3)
    }
    if (i.equals(1)) {
      turn.update(i, turn.get(i^1))  //(4)
      while (flag.get(i^1).equals(1) && turn.get(1).equals(turn.get(0))) {}  //(5)
    }
  }

  override def release_mutex(i: Int): Unit = {
    flag.update(i, 0)   //(6)
  }
}

/*
Proof of correctness of the algorithm.

*Mutual exclusion*. Assume the contrary, i.e. that both p0 and p1 are simultaneously in the critical section.
Note that they could not be let in by the flag(0) = 0 (flag(1) = 0 respectively)
predicate. Indeed, if it was the case, e.g., flag(1) was 0 when p0 was evaluating its predicate, then it would mean that
when p1 entered acquire_mutex , it first executed (1) , then executed update (4) and then, since p0 is in the critical section,
and wouldn't modify turn(0) while in the critical section, p1 would evaluate predicate at line (5) as true and hence wait
until the moment p0 left critical section.

(#) Also note that in case the update of turn(i) at (2) (or (4)) takes place before the read of turn(i^1) at (4) (or (2) respectively),
then obviously the process that first modified its turn value passes (the next process i^1 will necessarily set the value of its
turn variable to match the proper condition for the other process i to pass, and since turn(i) has already been set and won't change
until pi executes release_mutex, the condition cannot be altered by pi any more).

So, we when p0 was evaluating (3) for the last time, it saw turn(0) = turn(1), and when p1 was evaluating
(5) for the last time, it saw turn(0) != turn(1).

Due to (#), we can assume that at lines (2) and (4) both pi and pi^1 saw values of t_i^1 = turn(i^1) and t_i = turn(i) -
the values prior to any updates.
Since it is the case that either t_i = t_i^1 or t_i != t_i^1 , only one of pi , pi^1 must "decide" to adjust
the value for its turn in this situation - if t_i = t_i^1, then p1 will assign turn(1) the same value turn1_new = t_0 = t_1 (the old value of turn(1))
so it appears as if turn(1) was not changed, similarly with p0 and t_i != t_i^1 .
- assume that both processes saw t_i = t_i^1. This means that p0 decided to change turn(0), while p1 behaves the way as if it hadn't modified turn(1).
Then p0 couldn't've seen turn(0) = turn(1) at line (3).
- assume that both processes saw t_i != t_i^1 . Then p1 decided to change turn(1), while p0 decided to leave turn(0) as is.
This means that p1 couldn't see t_i != t_i^1 at line (5)

Mutual exclusion property is proved.

Deadlock freedom. This is obvious, since, in case one process doesn't take part in the competition, due to its flag(i) equal to 0 ,
the other process successfully passes (3) (or (5) respectively). Otherwise, when the processes are executing their wait statements at lines
(3) and (5), obviously neither turn(0) nor turn(1) are changing, and either turn(0) = turn(1) or turn(0) != turn(1) holds, so one of the processes
must pass.

Bounded bypass property. Assume that p0 and p1 are competing for the critical section and p0 wins, while p1 is waiting at line (5).
In case p0 will no longer be interested in the critical section, since it set flag(0) to 0 at (6), p1 will proceed to
the critical section after evaluating (5) after some time. In case p0 will be interested in the critical section again, it will proceed
to line (2) again. In the meantime, the value of turn(1) cannot change since p1 is in the waiting state and cannot execute (4) -
the only place where turn(1) is altered. Hence, after (2) is executed, we'll get turn(0) != turn(1), so that p1 can pass and p0 will
find itself in the waiting state.

End of the proof.


 */
