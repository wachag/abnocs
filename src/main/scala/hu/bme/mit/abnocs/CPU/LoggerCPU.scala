package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorRef, ActorSelection}
import hu.bme.mit.abnocs.Common.NOCMsg
import hu.bme.mit.abnocs.Logger.Logging

/**
  * Created by wachag on 2016.05.30..
  */
trait LoggerCPU extends CPU with Logging {

  override def handleMessage(m: NOCMsg): Unit = {
    super.handleMessage(m)
    logger ! m
  }

  override def generateMessage(): Option[NOCMsg] = {
    val tmp: Option[NOCMsg] = super.generateMessage()
/*    if (tmp.isDefined)
      logger ! tmp.get*/
    tmp
  }
}
