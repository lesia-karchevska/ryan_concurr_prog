package chapter2.fetch_and_add_mutex

import chapter2.common.MutexExerciser

object RunFAMutex extends App {

  MutexExerciser.testMutex(() => new FAMutex, 0, 20, 3000)
}
