package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorRef, ActorSelection}
import hu.bme.mit.abnocs.NOCMsg

/**
  * Created by wachag on 2016.05.30..
  */
trait LoggerCPU extends CPU {
  val logger:ActorSelection = context.actorSelection("/user/noc1/logger")

  override def generateMessage(): Option[NOCMsg] = {
    val tmp: Option[NOCMsg] = super.generateMessage()
    if (tmp.isDefined)
      logger ! tmp.get
    tmp
  }
}
