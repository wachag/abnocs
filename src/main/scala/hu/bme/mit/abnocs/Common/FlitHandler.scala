package hu.bme.mit.abnocs.Common

import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.language.postfixOps
import scala.concurrent.duration._
/**
  * Created by wachag on 2016.06.13..
  */
trait FlitHandler {
  def routeableMessageToFlitList(msg: RoutableMessage): List[Flit] = {
    var flitList: List[Flit] = List()
    msg match {
      case RoutableMessage(source, dest, payload) =>
        implicit val timeout:Timeout=Timeout(100 seconds)
        val uuid:Future[Int]=(UniqueID.uniqueID ? Next()).mapTo[Int]
        flitList = flitList :+ Flit(uuid,dest, source, 0, head = true, tail = false, payload.head)
        payload.drop(1).dropRight(1).foreach(b => {
          flitList = flitList :+ Flit(uuid,dest, source, 0, head = false, tail = false, b)
        })
        flitList = flitList :+ Flit(uuid,dest, source, 0, head = false, tail = true, payload.last)

    }
    flitList
  }

}
