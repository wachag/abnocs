package hu.bme.mit.abnocs.CPU

import hu.bme.mit.abnocs.Common.{Flit, FlitHandler, NOCMsg, RoutableMessage}

import scala.collection.immutable.HashMap

/**
  * Created by wachag on 2016.06.13..
  */
trait FlitMessageCPU extends CPU with FlitHandler {
  var inComingflitMap: HashMap[Int, List[Flit]] = HashMap()
  var outGoingFlitList: List[Flit] = List()

  override def generateMessage(): Option[NOCMsg] = {
    val l: Option[NOCMsg] = super.generateMessage()
    l.foreach((m: NOCMsg) => {
      m match {
        case f: Flit => outGoingFlitList = outGoingFlitList :+ f
        case msg: RoutableMessage =>
          outGoingFlitList = outGoingFlitList ++ routeableMessageToFlitList(msg)
        case _=>
      }
    })

    val h = outGoingFlitList.headOption
    outGoingFlitList = outGoingFlitList.drop(1)
    h
  }


  override def handleMessage(m: NOCMsg): Unit = {
    m match {
      case m: RoutableMessage => super.handleMessage(m)
      case Flit(src, dest, channel, head, tail, data) =>
        if (head)
          inComingflitMap = inComingflitMap + (src -> List(Flit(src, dest, channel, head, tail, data)))
        else {
          val x: List[Flit] = inComingflitMap.get(src).map(l => {
            l :+ Flit(src, dest, channel, head, tail, data)
          }).getOrElse(List())
          inComingflitMap = inComingflitMap.updated(src, x)
        }
        if (tail) {
          val s: String = inComingflitMap.get(src).map((l: List[Flit]) => {
            l.foldLeft("")((b, f) => {
              f match {
                case Flit(src_, dest_, channel_, head_, tail_, data_) => b + data_
              }
            })
          }
          ).getOrElse("UNKNOWN FLIT")

          super.handleMessage(RoutableMessage(src, dest, s))
          inComingflitMap = inComingflitMap - src
        }
      case _ =>
    }
  }

}
