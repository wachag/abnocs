package hu.bme.mit.abnocs.Buffer

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.{Tick, Tock, _}

/**
  * Created by wachag on 2016.06.08..
  */
class Buffer(reader: ActorRef, writer: ActorRef) extends NOCObject {
  override def receive: Receive = {
    case Start() => context.become(operation)
    case DiscoveryRequest() => sender() ! DiscoveryResponse(discovery)
  }

  def enqueue(msg: NOCMsg): Unit = {}

  def full(): Unit = {}

  def notFull(): Unit = {}

  def getMessageHead: Option[NOCMsg] = None

  def operation: Receive = {
    case Tick() =>
      getMessageHead.foreach(m => {
        reader ! m
      })
      sender() ! Tock()
    case msg: RoutableMessage => enqueue(msg)
    case msg: Flit => enqueue(msg)
    case Full() => full()
    case NotFull() => notFull()
  }

  override def discovery: List[ActorRef] = {
    super.discovery ++ List(reader)
  }
}

trait SimpleBuffer extends Buffer {
  var buffer: List[NOCMsg] = List()

  override def enqueue(msg: NOCMsg): Unit = {
    buffer = buffer :+ msg
  }

  override def getMessageHead: Option[NOCMsg] = {
    val hd: Option[NOCMsg] = buffer.headOption
    buffer = buffer.drop(1)
    hd
  }
}

trait SimpleBufferGenerator {
  val context: ActorContext

  def generateBuffer(id: Int, reader: ActorRef, writer: ActorRef): ActorRef = {
    val buffer: ActorRef = context.actorOf(Props(new Buffer(reader, writer) with SimpleBuffer with LoggerBuffer), "buffer" + id)
    buffer
  }
}
