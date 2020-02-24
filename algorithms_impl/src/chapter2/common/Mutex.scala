package chapter2.common

trait Mutex {
  def acquire_mutex(i: Int): Unit
  def release_mutex(i: Int): Unit
}
