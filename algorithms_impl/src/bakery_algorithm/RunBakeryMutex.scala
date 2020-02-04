package bakery_algorithm

import common.MutexExerciser

object RunBakeryMutex extends App {

  MutexExerciser.testMutex(() => new BakeryMutex(15), 0, 15, 3000)
}
