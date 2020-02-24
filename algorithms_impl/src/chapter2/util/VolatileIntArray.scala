package chapter2.util

class VolatileIntArray(length: Int) {

  val array = new Array[Int](length)
  @volatile
  var marker: Int  = 0

  def get(i: Int): Int = { marker; array(i) }
  def update(i: Int, value: Int) = { array(i) = value; marker = 0 }
}
