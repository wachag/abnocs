package hu.bme.mit.abnocs.CPU

import hu.bme.mit.abnocs.NOCMsg

/**
  * Created by wachag on 2016.05.30..
  */
trait LoggerCPU extends CPU {
  override def generateMessage(): Option[NOCMsg] = {
    val tmp: Option[NOCMsg] = super.generateMessage()
    if (tmp.isDefined) println("LoggerCPU " + tmp)
    tmp
  }
}
