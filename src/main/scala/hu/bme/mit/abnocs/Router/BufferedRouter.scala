package hu.bme.mit.abnocs.Router

import hu.bme.mit.abnocs.{Full, NOCMsg, NotFull}

/**
  * Created by wachag on 2016.06.08..
  */
trait BufferedRouter extends Router {
  override def handleBuffer(msg: NOCMsg): Unit =
    backRoute(sender()).foreach(a => {
      a ! msg
    })
}
