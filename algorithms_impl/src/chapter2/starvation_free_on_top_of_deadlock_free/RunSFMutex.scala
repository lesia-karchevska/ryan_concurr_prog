package chapter2.starvation_free_on_top_of_deadlock_free

import chapter2.common.MutexExerciser

object RunSFMutex extends App {
  MutexExerciser.testMutex(() => new SFMutex(10), 0, 10)
}
