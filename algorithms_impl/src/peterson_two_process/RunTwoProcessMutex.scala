package peterson_two_process

import common.MutexExerciser

object RunTwoProcessMutex extends App {

  MutexExerciser.testMutex(() => new Mutex, 0, 2)
}
