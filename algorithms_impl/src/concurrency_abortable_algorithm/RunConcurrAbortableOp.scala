package concurrency_abortable_algorithm

object RunConcurrAbortableOp extends App {

  @volatile
  private var start = false
  def getThread(i: Int, caop: CAO): Thread = {
    new Thread(() => {
      while (!start) {}
      val res = caop.concurr_abort_op(i)
      println("Process " + i + " obtained result: " + res)
    })
  }

  val caop = new CAO
  val threads = List.range(0, 60).map(i => getThread(i, caop))
  threads.foreach(t => t.start())
  Thread.sleep(100)
  start = true
}
