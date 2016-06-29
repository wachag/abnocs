package hu.bme.mit.abnocs

import akka.actor._
import hu.bme.mit.abnocs.Common._
import hu.bme.mit.abnocs.Logger.Logging

/**
  * Created by wachag on 2016.05.28..
  */
class Simulator extends Actor with Logging {
  private var items: List[ActorRef] = List()
  private var agenda: List[(ActorRef, WorkItem)] = List()
  UniqueID.uniqueID = context.actorOf(Props[UniqueID], name = "UniqueID")

  def receive = {
    case AddNOCObject(obj) =>
      logger ! AddNOCObject(obj)
      items = obj :: items
    case Start() =>

      items.foreach(item => item ! Start())
      items.foreach(item => item ! Tick())
      logger ! Tick()
      context.become(WaitTock(items.length))
  }

  def WaitTock(tocksToWait: Int): Receive = {
    case w: WorkItem => agenda = agenda :+(sender, w)
    case Tock() =>
      if (tocksToWait > 1) {
        context.become(WaitTock(tocksToWait - 1))
      }
      else {
        agenda.foreach(w => {
          w._2.to.tell(w._2.what, w._1)
        })
        agenda = List()
        items.foreach(item => item ! Tick())
        logger ! Tick()
        context.become(WaitTock(items.length))
      }
  }
}
