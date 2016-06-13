package hu.bme.mit.abnocs.Buffer

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs._

/**
  * Created by wachag on 2016.06.03..
  */
trait FifoBuffer extends Buffer with FlitHandler{
  val channel: Int = 0

  val size: Int = 5
  var buffer: List[Flit] = List()
  var readerFull: Boolean = false

  override def enqueue(msg: NOCMsg): Unit = {
    msg match {
      case msg:RoutableMessage =>
        buffer = buffer ++ routeableMessageToFlitList(msg)
      case msg: Flit => buffer = buffer :+ msg
      case _=>
    }
    if (buffer.length >= size) sender() ! Full()
  }

  override def full(): Unit = {
    super.full()
    readerFull = true
  }

  override def notFull(): Unit = {
    super.notFull()
    readerFull = false
  }

  override def getMessageHead: Option[NOCMsg] = if (readerFull) None
  else {
    val hd: Option[NOCMsg] = buffer.headOption
    buffer = buffer.drop(1)
    hd
  }

}

trait FifoBufferGenerator {
  val context: ActorContext

  def generateBuffer(id: Int, reader: ActorRef, writer: ActorRef): ActorRef = {
    val buffer: ActorRef = context.actorOf(Props(new Buffer(reader, writer) with FifoBuffer with LoggerBuffer), "buffer" + id)
    buffer
  }
}
