package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Router.{RingRouter, RingRouterGenerator}
import hu.bme.mit.abnocs.CPU._
import hu.bme.mit.abnocs.Topology._

class TestNOC extends Topology with RingTopologyGenerator with RingRouterGenerator with RandomCPUGenerator {
  override val ringSize: Int = 10
  override val numCPUs:Int=ringSize
  override val messageProbability:Double=0.001
  override def receive: Actor.Receive = {
    case Start() =>
      println("Start");
      generateTopology()
      val clk = context.actorOf(Props[Clock], name = "clock")
      simObjects.map((x: ActorRef) => {
        println("x " + x.toString())
        clk ! AddNOCObject(x)
      })
      clk ! Start()
    case Tick() =>
      println("Tick!")
      sender ! Tock()
  }
}