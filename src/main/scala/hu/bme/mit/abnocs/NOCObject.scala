package hu.bme.mit.abnocs
import akka.actor._

abstract class NOCObject() extends Actor {
   def discovery:List[ActorRef]=List()
}