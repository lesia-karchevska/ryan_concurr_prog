package chapter2.peterson_two_process

import chapter2.common.MutexExerciser

object RunTwoProcessMutex extends App {

  MutexExerciser.testMutex(() => new Mutex, 0, 2)
}
