package exercises_ch2.ex4

class Mutex4(delay: Int) extends common.Mutex {
  @volatile
  private var X: Int = -1
  @volatile
  private var Y: Int = -1
  @volatile
  private var allowed = true

  override def acquire_mutex(i: Int): Unit = {
    def go(): Unit = {
      while (!Y.equals(-1)) {}
      Y = i
      if (X.equals(i)) {
        allowed = false
      } else {
        Thread.sleep(3 * delay)
        if (!(Y.equals(i) && allowed)) go()
      }
    }
    X = i
    go()
    allowed = false
  }

  override def release_mutex(i: Int): Unit = {
    allowed = true
    Y = -1
  }
}

/*

Algorithm:

acquire_mutex(i)
  X <- i
  repeat
    wait (Y = -1)    (1)
    Y <- i           (2)
    if (X = i)       (3)
      allowed = false (4)
      return()
    else
      delay(3 * delta)
  until (Y = i && allowed)  (5)
  allowed = false
  return()

release_mutex(i)
  allowed <- true
  Y <- -1

*Proof of mutual exclusion property*.
There is a finite set of processes that read -1 from Y.
Let's make the following denotations:
- t^wY_i - the time when p_i wrote i to Y at (2).
- t^rY_i - the time when p_i read Y at (1)
- t^allowed=false_i - the time when p_i set allowed to false at (4)
- t^rAllowed_j - time at which p_j read allowed at (5)
- t^rY5_i - time at which p_i reads Y at (5)

Assume p_i and p_j are in the critical section simultaneously.
There are two cases for p_i:
*Case 1*. p_i was allowed by X = i verification. In this case p_i should be the last process that wrote to X among the processes that read
-1 from Y and hence p_j couldn't've been let by X = j, so it was let into the critical section by (Y = j && allowed). Since both p_i and p_j
read -1 from Y , we have that t^rY_j + delta < t^wY_i and t^rY_i + delta < t^wY_j (*). Also, t^wY_i < t^rY_i + delta and t^wY_j < t^rY_j + delta .
By the synchrony assumption, t^allowed=false_i < t^rY_i + 3 * delta (**). Also, t^rAllowed_j > t^wY_j + 3 * delta (***). By inequality (*),
(***) turns into t^rAllowed_j > t^rY_i + delta + 3 * delta = t^rY_i + 4 * delta > t^allowed=false_i by (**). The latter shows that p_j
couldn't've read true from allowed at line (5). (So basically we could execute delay(2 * delta) instead of delay(3 * delta))
*Case 2*. p_i was let by (Y = i && allowed). In this case, due to the previous item, p_j couldn't've been let in by X = j. Assume it was let in by
(Y = j && allowed) statement. Since both p_i and p_j read -1 from Y ,
we have that |t^wY_i - t^wY_j| < delta (*). Also, by the construction of the algorithm, we have that
t^rY5_i > t^wY_i + 3 * delta (**), t^rY5_j > t^wY_j + 3 * delta (***) . Without loss of generality, we may assume that t^wY_i > t^wY_j (^).
Hence, by (*) combined with (***), we have that t^rY5_j > t^wY_j + 3 * delta > t^wY_i - delta + 3 * delta = t^wY_i + 2 * delta ,
so, by assumption (^), we have that p_j couldn't've read j from Y at line (5) since p_i had already overwritten Y.



 */
