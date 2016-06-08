package hu.bme.mit.abnocs.Router

import akka.actor._
import hu.bme.mit.abnocs._

import scala.collection.immutable.HashMap

trait RouterGenerator {
  val context: ActorContext

  def generateRouter(id: Int): ActorRef = {
    val router0: ActorRef = context.actorOf(Props(new Router(id)), name = "router" + id)
    router0
  }
}

class Router(routerid: Int) extends NOCObject() {
  var processor: ActorRef = null
  val routerId: Int = routerid

  def addRoute(id: Int, obj: ActorRef):Unit = {}

  def receive: Actor.Receive = {
    case AddNOCObject(obj) =>
      processor = obj
      processor ! AddNOCObject(self)
    case AddRoute(id, obj) => addRoute(id, obj)
    case Start() => context.become(routing)
  }

  def routing: Actor.Receive = {
    case RoutableMessage(dest, msg) =>
      if (dest == routerid) processor ! RoutableMessage(dest, msg)
      else {
        routeToRouter(routePath(dest)) foreach (x => {
          x ! RoutableMessage(dest, msg)
        })
      }
    case Tick() =>
      sender ! Tock()
  }

  def routePath(id: Int): Option[Int] = None

  def routeToRouter(id: Option[Int]): Option[ActorRef] = None

  /*{
    val rand = Random.nextInt(routeMap.keys.size)
    routeMap.keys.toList(rand)
  }*/
}

trait SingleVCRouter extends Router {
  var routeMap: Map[Int, ActorRef] = new HashMap[Int, ActorRef]()

  override def addRoute(id: Int, obj: ActorRef): Unit = {
    routeMap = routeMap + (id -> obj)
  }
  override def routeToRouter(id: Option[Int]): Option[ActorRef] = id.flatMap(routeMap.get)
}


