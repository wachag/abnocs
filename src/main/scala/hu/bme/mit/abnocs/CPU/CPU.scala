package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs._

import scala.util.Random

/**
  * Created by wachag on 2016.05.28..
  */

trait CPUGenerator {
  val context: ActorContext

  def generateCPU(id:Int, router: ActorRef): ActorRef = {
    val cpu0: ActorRef = context.actorOf(Props(new CPU(router,id)),"CPU"+id)
    cpu0 ! AddNOCObject(router)
    router ! AddNOCObject(cpu0)
    cpu0
  }
}


class CPU(rout: ActorRef,id:Int) extends NOCObject() {
  var tickCount: Int = 0
  var router: ActorRef = rout
  val cpuId:Int=id
  override def discovery=super.discovery++List(router)

  def receive: Receive = {
    case Start() => context.become(generateTraffic)
    case AddNOCObject(routr) => this.router = routr
    case DiscoveryRequest() => sender()!DiscoveryResponse(discovery)
  }

  def generateMessage(): Option[NOCMsg] = None
  def handleMessage(m: NOCMsg):Unit={}
  def generateTraffic: Receive = {
    case m:RoutableMessage => handleMessage(m)
    case m:Flit=> handleMessage(m)
    case Tick() =>
      tickCount += 1
      generateMessage() foreach (msg => {
        router ! msg
      })
      sender() ! Tock()
  }
}
