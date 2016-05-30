package hu.bme.mit.abnocs.Router

import akka.actor._
import hu.bme.mit.abnocs._

import scala.collection.immutable.HashMap
import scala.util.Random

trait RouterGenerator {
  def generateRouter(context: ActorContext, id: Int): ActorRef = {
    val router0: ActorRef = context.actorOf(Props(new Router(id)), name = "router" + id)
    router0
  }
}

class Router(routerid: Int) extends NOCObject() {
  var processor: ActorRef = null
  val routerId: Int = routerid
  var routeMap: Map[Int, ActorRef] = new HashMap[Int, ActorRef]()

  def receive: Actor.Receive = {
    case AddNOCObject(obj) =>
      processor = obj
      processor ! AddNOCObject(self)
    case AddRoute(id, obj) => routeMap = routeMap + (id -> obj);
    case Start() => context.become(routing)
  }

  def routing: Actor.Receive = {
    case RoutableMessage(dest, msg) =>
      if (dest == routerid) processor ! RoutableMessage(dest, msg)
      else {
        val where = route(dest)
        if (routeMap contains where) {
          routeMap(where) ! RoutableMessage(dest, msg)
        }
        else {
          println("Dropping message\n")
        }
      }
    case Tick() =>
      sender ! Tock()
  }

  /* Random route*/
  def route(id: Int): Int = {
    val rand = Random.nextInt(routeMap.keys.size)
    routeMap.keys.toList(rand)
  }
}