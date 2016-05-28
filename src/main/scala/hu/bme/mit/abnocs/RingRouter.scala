package hu.bme.mit.abnocs

/**
  * Created by wachag on 28/05/2016.
  */
trait RingRoute extends Router {
  val ringSize: Int = 9

  def route(id: Int): Int = {
    println("Ring "+ringSize+ " Route from " + routerId + " to " + id + " via "+ (routerId + 1) % ringSize)
    (routerId + 1) % ringSize
  }
}

class RingRouter(id: Int, size: Int) extends Router(id) with RingRoute {
  override val ringSize: Int = size
}