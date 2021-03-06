package hu.bme.mit.abnocs.Router

import akka.actor.{ActorRef, ActorSelection}
import hu.bme.mit.abnocs.Common.AddRoute
import hu.bme.mit.abnocs.Logger.Logging

/**
  * Created by wachag on 2016.06.08..
  */
trait LoggerRouter extends Router with Logging{

  override def addRoute(id: Int, obj: ActorRef): Unit = {
    super.addRoute(id, obj)
    logger ! AddRoute(id, obj)
  }

  override def routePath(id: Int): Option[Int] = {

    val ret=super.routePath(id)
    logger ! ("Route to "+id+ " goes through "+ret.getOrElse(-1))
    ret
  }

}
