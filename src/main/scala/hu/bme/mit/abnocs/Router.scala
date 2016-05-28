package hu.bme.mit.abnocs

import akka.actor._

class Router(neighbours: List[ActorRef]) extends NOCObject(neighbours) {

  def receive: Actor.Receive = {
    case RoutableMessage(dest, msg) => {
      println(msg + " to " + dest)
      context.system.terminate()
    }
    case Tick() => {
      println("Tick in " + self.path.name + "!")
      sender ! Tock()
    }
  }
}