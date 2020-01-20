package concurrency_abortable_algorithm

import common.MutexExerciser

object RunFastMutex extends App {

  MutexExerciser.testMutex(() => new FastMutex(10), 0, 10)
}
