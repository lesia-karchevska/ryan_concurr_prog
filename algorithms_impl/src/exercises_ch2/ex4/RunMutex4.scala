package exercises_ch2.ex4

import common.MutexExerciser

object RunMutex4 extends App {
  MutexExerciser.testMutex(() => new Mutex4(25), 0, 10, 20000)
}
