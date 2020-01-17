package two_process_generalised_for_n

import common.MutexExerciser

object RunNProcessMutex extends App {

  MutexExerciser.testMutex(() => new Mutex(10), 0, 10)
}
