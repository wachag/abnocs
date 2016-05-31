package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Router.{RingRouter, RingRouterGenerator}
import hu.bme.mit.abnocs.CPU._
import hu.bme.mit.abnocs.Topology._
import hu.bme.mit.abnocs.Logger._
class TestNOC extends Topology with RingTopologyGenerator with RingRouterGenerator with RandomCPUGenerator {
  override val ringSize: Int = 10
  override val numCPUs:Int=ringSize
  override val messageProbability:Double=0.001
  override def receive: Actor.Receive = {
    case Start() =>
      generateTopology()
      val logger = context.actorOf(Props[Logger], name="logger")
      println(logger.path)
      val clk = context.actorOf(Props[Clock], name = "clock")
      simObjects.map((x: ActorRef) => {
        logger ! "Adding object "+x.path.name
        clk ! AddNOCObject(x)
      })
      clk ! Start()
    case Tick() =>
      sender ! Tock()
  }
}