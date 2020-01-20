package concurrency_abortable_algorithm

class CAO {

  @volatile
  private var x: Int = 0
  @volatile
  private var y: Int = -1
  private val ABORT_1: String = "abort_1"
  private val ABORT_2: String = "abort_2"
  private val COMMIT: String = "commit"

  def concurr_abort_op(i: Int): String = {
    x = i
    if (!y.equals(-1)) {
      ABORT_1
    } else {
      y = i
      if (x.equals(i)) {
        COMMIT
      } else {
        ABORT_2
      }
    }
  }
}
