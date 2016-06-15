package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Buffer.{FifoBufferGenerator, SimpleBufferGenerator}
import hu.bme.mit.abnocs.Router.RingRouterGenerator
import hu.bme.mit.abnocs.CPU._
import hu.bme.mit.abnocs.Topology._

class TestNOC extends Topology with RingTopologyGenerator with RingRouterGenerator with RandomCPUGenerator with FifoBufferGenerator {
  override val ringSize: Int = 300
  override val numCPUs: Int = ringSize
  override val messageProbability: Double = 0.01
  val logger:ActorSelection = context.actorSelection("/user/logger")

  override def receive: Actor.Receive = {
    case AddNOCObject(x) => addSimObject(x)
    case Start() =>
      generateTopology()
      val clk = context.actorOf(Props[Clock], name = "clock")
      simObjects.foreach {
        (x: ActorRef) => {
          clk ! AddNOCObject(x)

        }
      }
      simObjects.foreach((x: ActorRef) => {
        x ! DiscoveryRequest()
      })

      clk ! Start()
    case Tick() =>
      sender ! Tock()
    case DiscoveryResponse(l: List[ActorRef]) =>
      l.foreach((x: ActorRef) => {
        logger ! AddEdge(sender(),x)
      })
  }
}