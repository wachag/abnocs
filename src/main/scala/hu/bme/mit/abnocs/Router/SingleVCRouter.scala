package hu.bme.mit.abnocs.Router

import akka.actor.ActorRef

import scala.collection.immutable.HashMap

/**
  * Created by wachag on 2016.06.08..
  */
trait SingleVCRouter extends Router {
  var routeMap: Map[Int, ActorRef] = new HashMap[Int, ActorRef]()

  override def addRoute(id: Int, obj: ActorRef): Unit = {
    routeMap = routeMap + (id -> obj)
  }
  override def routeToRouter(id: Option[Int]): Option[ActorRef] = id.flatMap(routeMap.get)
}
