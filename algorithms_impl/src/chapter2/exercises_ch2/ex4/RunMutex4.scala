package chapter2.exercises_ch2.ex4

import chapter2.common.MutexExerciser

object RunMutex4 extends App {
  MutexExerciser.testMutex(() => new Mutex4(25), 0, 10, 20000)
}
