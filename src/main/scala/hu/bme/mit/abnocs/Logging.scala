package hu.bme.mit.abnocs

import akka.actor.{Actor, ActorSelection}

/**
  * Created by wachag on 2016.06.10..
  */
trait Logging extends Actor{
  val logger:ActorSelection = context.actorSelection("/user/logger")

}
