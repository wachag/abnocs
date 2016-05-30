package hu.bme.mit.abnocs.Topology

import akka.actor.{ActorContext, ActorRef}
import hu.bme.mit.abnocs.NOCObject

/**
  * Created by wachag on 2016.05.30..
  */
abstract class Topology extends NOCObject {
  var simObjects: List[ActorRef] = List()

  def generateTopology(): Unit

  def generateRouter(context: ActorContext, id:Int): ActorRef

  def generateCPU(context: ActorContext, router: ActorRef): ActorRef

  def addSimObject(obj: ActorRef): Unit = {
    simObjects = simObjects :+ obj
  }
}