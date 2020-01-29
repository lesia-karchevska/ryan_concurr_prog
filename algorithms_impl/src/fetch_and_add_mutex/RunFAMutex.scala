package fetch_and_add_mutex

import common.MutexExerciser

object RunFAMutex extends App {

  MutexExerciser.testMutex(() => new FAMutex, 0, 20, 3000)
}
