package chapter2.two_process_generalised_for_n

import chapter2.common.MutexExerciser

object RunNProcessMutex extends App {

  MutexExerciser.testMutex(() => new Mutex(10), 0, 10)
}
