package hu.bme.mit.abnocs

/**
  * Created by wachag on 2016.06.13..
  */
trait FlitHandler {
  def routeableMessageToFlitList(msg: RoutableMessage): List[Flit] = {
    var flitList: List[Flit] = List()
    msg match {
      case RoutableMessage(source, dest, payload) =>
        flitList = flitList :+ Flit(dest, source, 0, head = true, tail = false, payload.head)
        payload.tail.init.foreach(b => {
          flitList = flitList :+ Flit(dest, source, 0, head = false, tail = false, b)
        })
        flitList = flitList :+ Flit(dest, source, 0, head = false, tail = true, payload.last)

    }
    flitList
  }

}
