package hu.bme.mit.abnocs.Router

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.Router

/**
  * Created by wachag on 28/05/2016.
  */

trait RingRouterGenerator {
  val ringSize: Int = 9
  def generateRouter(context: ActorContext, id: Int): ActorRef = {
    val router0: ActorRef = context.actorOf(Props(new RingRouter(id,ringSize)), name = "router" + id)
    router0
  }
}


trait RingRoute extends Router {
  val ringSize: Int = 9

  override def route(id: Int): Int = {
    println("Ring "+ringSize+ " Route from " + routerId + " to " + id + " via "+ (routerId + 1) % ringSize)
    (routerId + 1) % ringSize
  }
}

class RingRouter(id: Int, size: Int) extends Router(id) with RingRoute {
  override val ringSize: Int = size
}