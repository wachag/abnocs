package hu.bme.mit.abnocs.Topology

import akka.actor.{ActorContext, ActorRef}
import hu.bme.mit.abnocs.AddRoute

/**
  * Created by wachag on 2016.05.30..
  */
trait RingTopologyGenerator extends Topology {
  val ringSize: Int = 9
  val context: ActorContext

  def generateTopology(): Unit = {
    var i: Int = 0
    var routers: List[ActorRef] = List()
    for (i <- 0 until ringSize) {
      val nrouter: ActorRef = generateRouter(context, i)
      val nCPU: ActorRef = generateCPU(context, nrouter)

      routers = routers :+ nrouter
      addSimObject(nrouter)
      addSimObject(nCPU)
    }
    for (i <- 0 until ringSize) {
      val nid = (i + 1) % ringSize
      routers(i) ! AddRoute(nid, routers(nid))
    }

  }
}
