package hu.bme.mit.abnocs.Router

import akka.actor._
import hu.bme.mit.abnocs.Common._
import hu.bme.mit.abnocs._

import scala.collection.immutable.HashMap

trait RouterGenerator {
  val context: ActorContext

  def generateRouter(id: Int): ActorRef = {
    val router0: ActorRef = context.actorOf(Props(new Router(id)), name = "router" + id)
    router0
  }
}

/**
  * An abstract router implementation
  *
  * A router handles messages sent from other routers or from a processor, or a FIFO buffer.
  *
  * @param routerid the unique identifier of the router (and its CPU) in the NoC.
  *                 If the destination of the message handled is this id, it will be sent to the processor instead of a router.
  */
class Router(routerid: Int) extends NOCObject() with Deferrable {
  /** The processor the router is associated with */
  var processor: ActorRef = null
  val routerId: Int = routerid

  /**
    * Adds a connection between the router and an NoC actor
    *
    * Each router implementation can fill its routing table according to addRoute
    *
    * @param id  the id of the NoC actor
    * @param obj the reference for the Actor
    */
  def addRoute(id: Int, obj: ActorRef): Unit = {}

  def receive: Actor.Receive = {
    case AddNOCObject(obj) =>
      processor = obj
      processor ! AddNOCObject(self)
    case AddRoute(id, obj) => addRoute(id, obj)
    case DiscoveryRequest() => sender()!DiscoveryResponse(discovery)
    case Start() => context.become(routing)

  }

  def handleBuffer(msg: NOCMsg): Unit = {}

  def routing: Actor.Receive = {
    case RoutableMessage(source,dest, msg) =>
      if (dest == routerid) processor ! RoutableMessage(source,dest, msg)
      else {
        routeToRouter(routePath(dest)) foreach (x => {
          defer(WorkItem(x,RoutableMessage(source,dest, msg)))
        })
      }
    case Flit(source,dest,channel,head,tail,data) =>
      if (dest == routerid) processor ! Flit(source,dest, channel,head,tail,data)
      else {
        routeToRouter(routePath(dest)) foreach (x => {
          defer(WorkItem(x,Flit(source,dest, channel,head,tail,data)))
        })
      }
    case msg@Full() => handleBuffer(msg)
    case msg@NotFull() => handleBuffer(msg)
    case Tick() =>
      executeDeferred(sender())
      sender ! Tock()
  }

  def routePath(id: Int): Option[Int] = None

  def routeToRouter(id: Option[Int]): Option[ActorRef] = None

  def backRoute(output: ActorRef): Option[ActorRef] = None

  override def discovery: List[ActorRef] = super.discovery ++ List(processor)
}



