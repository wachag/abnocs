package hu.bme.mit.abnocs.Buffer

import hu.bme.mit.abnocs.{Logging, NOCMsg}

/**
  * Created by wachag on 2016.06.10..
  */
trait LoggerBuffer extends Buffer with Logging{
  override def enqueue(msg: NOCMsg): Unit = {
    logger ! msg
    super.enqueue(msg)
  }

  override def getMessageHead: Option[NOCMsg] = super.getMessageHead
}
