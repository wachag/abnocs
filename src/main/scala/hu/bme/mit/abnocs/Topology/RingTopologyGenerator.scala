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
      val nrouter: ActorRef = generateRouter(i)
      val nCPU: ActorRef = generateCPU(i, nrouter)

      routers = routers :+ nrouter
      addSimObject(nrouter)
      addSimObject(nCPU)
    }
    for (i <- 0 until ringSize) {
      val nid = (i + 1) % ringSize
      val buffer1 = generateBuffer(2 * i, routers(i), routers(nid))
      val buffer2 = generateBuffer(2 * i + 1, routers(nid), routers(i))
      addSimObject(buffer1)
      addSimObject(buffer2)
      routers(i) ! AddRoute(nid, buffer2)
      routers(nid) ! AddRoute(i, buffer1)
    }

  }
}
