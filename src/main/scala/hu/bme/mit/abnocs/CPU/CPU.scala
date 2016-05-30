package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs._

import scala.util.Random

/**
  * Created by wachag on 2016.05.28..
  */

trait CPUGenerator {
  val context: ActorContext

  def generateCPU(router: ActorRef): ActorRef = {
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

  def generateMessage(): Option[NOCMsg] = {
    if (tickCount == 0) {
      val i: Int = (Random.nextInt())
      println("Sending message to " + i)
      return Option(RoutableMessage(i, "hello"))
    }
    return None
  }

  def generateTraffic: Receive = {
    case RoutableMessage(dest, msg) =>
      println(tickCount + ": " + msg + " to " + dest + " arrived")
    case Tick() =>
      tickCount += 1
      generateMessage() foreach (msg => {
        router ! msg
      })
      sender() ! Tock()
  }
}
