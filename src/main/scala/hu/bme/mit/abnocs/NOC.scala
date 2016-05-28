package hu.bme.mit.abnocs

import akka.actor._

class NOC extends NOCObject(List()) {

  def receive: Actor.Receive = {
    case Start() => {
      val router1 = context.actorOf(Props(new Router(List())), name = "router1")
      println("Start3");
      val router2 = context.actorOf(Props(new Router(List())), name = "router2")
      println("Start4");
      val clk = context.actorOf(Props[Clock], name = "clock")
      println("Start5");
      clk ! AddNOCObject(router1)
      clk ! AddNOCObject(router2)
      clk ! Start()

    }
    case Tick() => {
      println("Tick!")
      sender ! Tock()
    }
  }
}