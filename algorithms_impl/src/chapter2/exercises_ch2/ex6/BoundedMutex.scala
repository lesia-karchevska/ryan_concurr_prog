package chapter2.exercises_ch2.ex6

import chapter2.common.SafeRegister

/*
Ex. 6, part 1, 2 (Answers are after implementation)
 */

class BoundedMutex(n: Int, BOUND: Int) extends chapter2.common.Mutex {
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
    val newDate = date.map(x => x.read).max + 1
    if (newDate > BOUND) {
      Range(0, n).foreach(i => date(i).write(i))
    } else {
      date(i).write(newDate)
    }
    stage(i).write(false)
    flag(i).write(false)
  }
}


/*
Ex. 6

part 1. Show that the safety property does not depend on BOUND.

That the safety property doesn't depend on the value of BOUND follows directly from the proof of the mutual exclusion
for Aravind's algorithm. date(i) values do not influence mutual exclusion, they only influence the order in which
processes get access to the critical section.

part 2. Let x \in { 1, ..., n } . Which type of liveliness property is satisfied when BOUND = x + n,
where n is the number of processes ?

We claim that in case BOUND = x + n, then for any i \in {1, ..., x } in case the process p_i competes for the critical section,
it is guaranteed to encounter at most one restart before it executes the critical section. For any i > x we have that p_i is not
guaranteed to enter the critical section.

Indeed, as in the proof of Theorem 11, consider a process p_i, i < x, that executes acquire_mutex method while reset happens.
Assume that some process p_j is the first process that executes after reset. Note that in case x = 1, the only process guaranteed to
terminate execution of acquire_mutex is p_1, so there is nothing to prove (indeed, at the beginning we have date(i) = i for all i,
after ). So assume that x > 1.
Then in release_mutex(j) the value date(j) is updated to n + 1. Since flag(i) = true and date(i) < date(j)
(the last inequality follows from the fact that date(i) was reset to i during reset, while date(j) was set to n + 1 > i).
In the worst case, p_i is p_x, and processes that compete with p_x include all p_j, j < x. Then all these p_j enter critical section
before p_x (since reset happened and date(j) = j), so max(date(1), ..., date(n)) will be equal to n + x

 */