package chapter2.exercises_ch2.ex1

import chapter2.common.MutexExerciser

object RunMutex1 extends App {

  MutexExerciser.testMutex(() => new Mutex1, 0, 2, 20000)
}
