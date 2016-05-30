package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs._

import scala.util.Random

/**
  * Created by wachag on 2016.05.28..
  */

trait CPUGenerator {
  def generateCPU(context: ActorContext, router: ActorRef): ActorRef = {
    val cpu0: ActorRef = context.actorOf(Props(new CPU(router)))
    cpu0 ! AddNOCObject(router)
    router ! AddNOCObject(cpu0)
    cpu0
  }
}


class CPU(rout: ActorRef) extends NOCObject() {
  var tickCount: Int = 0
  var router: ActorRef = rout


  def receive: Receive = {
    case Start() => context.become(generateTraffic)
    case AddNOCObject(routr) => this.router = routr
  }

  def generateTraffic: Receive = {
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
