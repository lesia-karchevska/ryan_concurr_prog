package chapter2.bakery_algorithm

import chapter2.common.MutexExerciser

object RunBakeryMutex extends App {

  MutexExerciser.testMutex(() => new BakeryMutex(15), 0, 15, 3000)
}
