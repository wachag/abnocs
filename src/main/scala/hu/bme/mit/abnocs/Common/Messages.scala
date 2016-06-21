package hu.bme.mit.abnocs.Common

import akka.actor.ActorRef

sealed trait NOCMsg

case class Start() extends NOCMsg

case class AddNOCObject(obj: ActorRef) extends NOCMsg

case class Tick() extends NOCMsg

case class Tock() extends NOCMsg

case class RoutableMessage(source: Int, dest: Int, payload: String) extends NOCMsg

case class Flit(source: Int, dest: Int ,channel: Int, head: Boolean, tail: Boolean, data: Char) extends NOCMsg

case class AddRoute(id: Int, router: ActorRef) extends NOCMsg

case class Full() extends NOCMsg
case class NotFull() extends NOCMsg

case class Empty() extends NOCMsg

case class DiscoveryRequest() extends NOCMsg
case class DiscoveryResponse(neighbours:List[ActorRef]) extends NOCMsg

case class AddEdge(start:ActorRef,end:ActorRef) extends NOCMsg

case class WorkItem(to: ActorRef, what: NOCMsg)