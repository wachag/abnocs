package hu.bme.mit.abnocs

import akka.actor.ActorRef

/**
  * Created by wachag on 2016.05.28..
  */
class CPU(rout: ActorRef) extends NOCObject(List()) {
  var tickCount: Int = 0
  var router: ActorRef = rout

  def receive: Receive = {
    case AddNOCObject(router) => this.router=router
    case Tick() => {
      tickCount += 1
      if(tickCount % 10 ==8)router!RoutableMessage(0,"hello")
      sender() ! Tock()
    }
  }
}
