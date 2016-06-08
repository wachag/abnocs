package hu.bme.mit.abnocs.FIFO

import akka.actor.Actor.Receive
import akka.actor.ActorRef
import hu.bme.mit.abnocs._
import sun.font.TrueTypeFont

/**
  * Created by wachag on 2016.06.03..
  */
class Fifo(size: Int, channel: Int, reader: ActorRef, writer: ActorRef) extends NOCObject {
  var buffer: List[Flit] = List()

  override def receive: Receive = {
    case Start => context.become(operation)
  }

  def enqueue(msg: RoutableMessage): Unit = {
    msg match {
      case RoutableMessage(dest, payload) =>
        buffer = buffer :+ Flit(dest, channel, head = true, tail = false, payload.head)
        payload.tail.init foreach (b => {
          buffer = buffer :+ Flit(dest, channel, head = false, tail = false, b)
        })
        buffer = buffer :+ Flit(dest, channel, head = false, tail = true, payload.last)
    }
  }

  def operation: Receive = {
    case msg: RoutableMessage => enqueue(msg)
      if (buffer.length >= size)
        writer ! Full()
    case f: Flit => buffer = buffer :+ f
      if (buffer.length >= size)
        writer ! Full()

    case Full() =>
      context.become(onlyReceive)
    case Tick() =>
      if (buffer.nonEmpty) {
        reader ! buffer.head
        buffer = buffer.tail
      }
      if (buffer.length < size)
        writer ! NotFull()
      sender ! Tock()

  }

  def onlyReceive: Receive = {
    case msg: RoutableMessage => enqueue(msg)
      if (buffer.length >= size)
        writer ! Full()
    case f: Flit => buffer = buffer :+ f
      if (buffer.length >= size)
        writer ! Full()
    case NotFull() =>
      context.become(operation)
    case Tick() => sender() ! Tock()
  }

}
