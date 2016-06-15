package hu.bme.mit.abnocs

import akka.actor._

/**
  * Created by wachag on 2016.05.28..
  */
class Clock extends Actor with Logging {
  private var items: List[ActorRef] = List()

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
    case Tock() =>
      if (tocksToWait > 1) {
        context.become(WaitTock(tocksToWait - 1))
      }
      else {
        items.foreach(item => item ! Tick())
        logger ! Tick()
        context.become(WaitTock(items.length))
      }
  }
}
