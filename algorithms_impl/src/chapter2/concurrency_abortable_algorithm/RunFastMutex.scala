package chapter2.concurrency_abortable_algorithm

import chapter2.common.MutexExerciser

object RunFastMutex extends App {

  MutexExerciser.testMutex(() => new FastMutex(10), 0, 10)
}
