package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Router.RingRouterGenerator
import hu.bme.mit.abnocs.CPU._
import hu.bme.mit.abnocs.FIFO.SimpleBufferGenerator
import hu.bme.mit.abnocs.Topology._
class TestNOC extends Topology with RingTopologyGenerator with RingRouterGenerator with RandomCPUGenerator with SimpleBufferGenerator{
  override val ringSize: Int = 1000
  override val numCPUs:Int=ringSize
  override val messageProbability:Double=0.001
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
      clk ! Start()
    case Tick() =>
      sender ! Tock()
  }
}