package hu.bme.mit.abnocs.Router

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.Router

/**
  * Created by wachag on 28/05/2016.
  */

trait RingRouterGenerator {
  val ringSize: Int = 9
  val context: ActorContext
  def generateRouter(id: Int): ActorRef = {
    val router0: ActorRef = context.actorOf(Props(new RingRouter(id,ringSize)), name = "router" + id)
    router0
  }
}


trait RingRoute extends Router {
  val ringSize: Int = 9

  /*override def route(id: Int): Int = {
    super.route(id)
    (routerId + 1) % ringSize
  }*/
  override def routePath(id: Int): Option[Int] = {
    super.routePath(id)
    Some((routerId + 1) % ringSize)
  }
}

class RingRouter(id: Int, size: Int) extends Router(id) with SingleVCRouter with RingRoute with LoggerRouter{
  override val ringSize: Int = size
}