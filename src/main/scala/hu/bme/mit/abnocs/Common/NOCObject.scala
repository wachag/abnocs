package hu.bme.mit.abnocs.Common

import akka.actor.{Actor, ActorRef}

/**
  * Created by wachag on 2016.06.21..
  */
abstract class NOCObject() extends Actor {
  def discovery: List[ActorRef] = List()
}
