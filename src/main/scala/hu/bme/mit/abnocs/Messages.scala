package hu.bme.mit.abnocs

import akka.actor.ActorRef

sealed trait NOCMsg
case class Start() extends NOCMsg
case class AddNOCObject(obj: ActorRef) extends NOCMsg
case class Tick() extends NOCMsg
case class Tock() extends NOCMsg
case class RoutableMessage(dest: Int, payload: String) extends NOCMsg
case class Flit(dest: Int, channel: Int, valid: Boolean, tail: Boolean, data: Int) extends NOCMsg


