package fischer_mutex

import common.MutexExerciser

object RunFischerMutex extends App {

  MutexExerciser.testMutex(() => new FischerMutex(1), 0, 20, 3000)
}

