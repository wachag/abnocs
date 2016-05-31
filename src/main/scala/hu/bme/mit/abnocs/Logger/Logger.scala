package hu.bme.mit.abnocs.Logger

import akka.actor.Actor
import akka.actor.Actor.Receive


/**
  * Created by wachag on 2016.05.31..
  */
class Logger extends Actor {
  override def receive: Receive = {
    case msg => println(msg)
  }
}
