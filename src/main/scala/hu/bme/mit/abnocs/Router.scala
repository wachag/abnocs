package hu.bme.mit.abnocs

import akka.actor._

import scala.collection.immutable.HashMap

abstract class Router(routerid: Int) extends NOCObject() {
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
        else{
          println("Dropping message\n")
        }
      }
    case Tick() =>
      //      println("Tick in " + self.path.name + "!")
      sender ! Tock()
  }

  def route(id: Int): Int
}