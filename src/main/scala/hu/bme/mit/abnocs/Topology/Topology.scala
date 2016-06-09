package hu.bme.mit.abnocs.Topology

import akka.actor.{ActorContext, ActorRef}
import hu.bme.mit.abnocs.NOCObject

/**
  * Created by wachag on 2016.05.30..
  */
abstract class Topology extends NOCObject {
  var simObjects: List[ActorRef] = List()

  def generateTopology(): Unit
  def generateBuffer(id:Int, reader: ActorRef, writer:ActorRef):ActorRef
  def generateRouter(id: Int): ActorRef

  def generateCPU(id:Int,router: ActorRef): ActorRef

  def addSimObject(obj: ActorRef): Unit = {
    simObjects = simObjects :+ obj
  }
}
