package chapter2.test_and_set_mutex

import chapter2.common.MutexExerciser

object RunTSMutex extends App {
  MutexExerciser.testMutex(() => new TSMutex, 1, 17)
}
