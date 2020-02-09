package exercises_ch2.ex1

import common.MutexExerciser

object RunMutex1 extends App {

  MutexExerciser.testMutex(() => new Mutex1, 0, 2, 20000)
}
