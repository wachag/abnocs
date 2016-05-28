package hu.bme.mit.abnocs

import akka.actor.Actor.Receive
import akka.actor.ActorRef

import scala.util.Random

/**
  * Created by wachag on 2016.05.28..
  */

class CPU(rout: ActorRef) extends NOCObject() {
  var tickCount: Int = 0
  var router: ActorRef = rout


  def receive: Receive = {
    case Start() =>
    case AddNOCObject(routr) => this.router = routr
    case RoutableMessage(dest, msg) =>
      println(tickCount + ": " + msg + " to " + dest + " arrived")
      //context.system.terminate()

    case Tick() =>
      tickCount += 1
      if (tickCount % 10 == 8) {
        val i: Int = (Random.nextInt() % 3 + 3) % 3
        println("Sending message to " + i)
        router ! RoutableMessage(i % 3, "hello")
      }
      if (tickCount % 100 == 88) context.system.terminate()
      sender() ! Tock()
  }
}
