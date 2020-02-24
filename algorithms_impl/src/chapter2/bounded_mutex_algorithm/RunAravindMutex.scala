package chapter2.bounded_mutex_algorithm

import chapter2.common.MutexExerciser

object RunAravindMutex extends App {

  MutexExerciser.testMutex(() => new AravindMutex(15), 0, 15, 3000)
}
