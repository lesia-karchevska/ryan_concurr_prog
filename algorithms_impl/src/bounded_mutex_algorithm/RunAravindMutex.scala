package bounded_mutex_algorithm

import common.MutexExerciser

object RunAravindMutex extends App {

  MutexExerciser.testMutex(() => new AravindMutex(15), 0, 15, 3000)
}
