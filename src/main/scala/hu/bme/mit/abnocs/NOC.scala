package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Router.RingRouter
import hu.bme.mit.abnocs.CPU.CPU

class NOC extends NOCObject() {

  def receive: Actor.Receive = {
    case Start() =>
      val router0 = context.actorOf(Props(new RingRouter(0, 3)), name = "router0")
      val router1 = context.actorOf(Props(new RingRouter(1, 3)), name = "router1")
      val router2 = context.actorOf(Props(new RingRouter(2, 3)), name = "router2")
      val cpu0 = context.actorOf(Props(new CPU(router0,0)))
      val cpu1 = context.actorOf(Props(new CPU(router1,1)))
      val cpu2 = context.actorOf(Props(new CPU(router2,2)))
      router0 ! AddNOCObject(cpu0)
      router1 ! AddNOCObject(cpu1)
      router2 ! AddNOCObject(cpu2)
      router0 ! AddRoute(1, router1)
      router1 ! AddRoute(2, router2)
      router2 ! AddRoute(0, router0)
      val clk = context.actorOf(Props[Clock], name = "clock")
      clk ! AddNOCObject(router0)
      clk ! AddNOCObject(router1)
      clk ! AddNOCObject(router2)
      clk ! AddNOCObject(cpu0)
      clk ! AddNOCObject(cpu0)
      clk ! AddNOCObject(cpu0)
      clk ! Start()
    case Tick() =>
      println("Tick!")
      sender ! Tock()
  }
}