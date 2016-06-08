package hu.bme.mit.abnocs.FIFO

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.{NotFull, Tick, Tock, _}

/**
  * Created by wachag on 2016.06.08..
  */
class Buffer(reader: ActorRef, writer: ActorRef) extends NOCObject {
  override def receive: Receive = {
    case Start() => context.become(operation)
  }

  def enqueue(msg: NOCMsg): Unit = {}

  def getMessageHead: Option[NOCMsg] = None

  def operation: Receive = {
    case Tick() =>
      getMessageHead.foreach(m => {
        reader ! m
      })
      sender() ! Tock()
    case msg : RoutableMessage => enqueue(msg)
  }
}

trait SimpleBuffer extends Buffer {
  var s: List[NOCMsg] = List()

  override def enqueue(msg: NOCMsg): Unit = {
    s = s :+ msg
  }

  override def getMessageHead: Option[NOCMsg] = {
    s.headOption
  }
}

trait SimpleBufferGenerator {
  val context: ActorContext

  def generateBuffer(reader: ActorRef, writer: ActorRef): ActorRef = {
    val buffer: ActorRef = context.actorOf(Props(new Buffer(reader, writer) with SimpleBuffer))
    buffer
  }
}
