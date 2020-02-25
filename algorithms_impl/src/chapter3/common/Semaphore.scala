package chapter3.common

class Semaphore(v: Int, max: Int) {

  @volatile
  private var value = v

  def up() = synchronized {
    while (value == max) wait
    value = value + 1
    notify
  }

  def down() = synchronized {
    while(value == 0) wait
    value = value - 1
    notify
  }
}
