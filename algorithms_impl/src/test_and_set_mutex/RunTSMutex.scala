package test_and_set_mutex

import common.MutexExerciser

object RunTSMutex extends App {
  MutexExerciser.testMutex(() => new TSMutex, 1, 17)
}
