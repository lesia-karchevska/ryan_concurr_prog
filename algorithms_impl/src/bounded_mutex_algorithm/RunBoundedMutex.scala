package bounded_mutex_algorithm

import common.MutexExerciser

object RunBoundedMutex extends App {

  MutexExerciser.testMutex(() => new BoundedMutex(15), 0, 15, 3000)
}
