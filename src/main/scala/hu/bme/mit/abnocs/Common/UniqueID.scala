package hu.bme.mit.abnocs.Common

import akka.actor.{Actor, ActorRef}

/**
  * Created by wachag on 2016.06.29..
  */
object UniqueID {
  var uniqueID:ActorRef=null

}

class UniqueID extends Actor{
  var current=0
  println(self.path.name+" "+self.path)
  override def receive: Receive = {
    case Next()=> sender()! current
      current=current+1
    case _=>
  }
}
