package chapter2.fetch_and_add_mutex

import chapter2.common.Mutex

class FAMutex extends Mutex {

  private val faMock = new FAMock

  @volatile
  private var next: Int = 1

  override def acquire_mutex(i: Int): Unit = {
    val my_turn = faMock.fetchAndAdd()
    while (!my_turn.equals(next)) {}
  }

  override def release_mutex(i: Int): Unit = {
    next = next + 1
  }
}
