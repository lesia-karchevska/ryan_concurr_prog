package chapter2.test_and_set_mutex

import chapter2.common.Mutex

class TSMutex extends Mutex {

  private val tsMock = new TSMock

  override def acquire_mutex(i: Int): Unit = {
    while(tsMock.testAndSet().equals(0)){}
  }

  override def release_mutex(i: Int): Unit = {
    tsMock.reset()
  }
}
