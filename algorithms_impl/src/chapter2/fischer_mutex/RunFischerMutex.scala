package chapter2.fischer_mutex

import chapter2.common.MutexExerciser

object RunFischerMutex extends App {

  MutexExerciser.testMutex(() => new FischerMutex(1), 0, 20, 3000)
}

