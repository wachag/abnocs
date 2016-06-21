package hu.bme.mit.abnocs.Logger

import akka.actor.Actor
import akka.actor.Actor.Receive
import hu.bme.mit.abnocs.Common.{NOCMsg, Tick}


/**
  * Created by wachag on 2016.05.31..
  */
class Logger extends Actor {
  var clockCount: Int = 0

  def log(msg: NOCMsg): NOCMsg = {
    msg match {
      case Tick() => clockCount = clockCount + 1
      case m =>
    }
    msg
  }

  override def receive: Receive = {
    case msg: NOCMsg => log(msg)
    case m =>
  }
}
