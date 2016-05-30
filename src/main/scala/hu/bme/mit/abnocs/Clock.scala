package hu.bme.mit.abnocs

import akka.actor._

/**
  * Created by wachag on 2016.05.28..
  */
class Clock extends Actor {
  private var items: List[ActorRef] = List()

  def receive = {
    case AddNOCObject(obj) =>
      println("Adding "+obj.toString())
      items = obj::items
    case Start() =>
      for (item <- items) item ! Start()
      for (item <- items) item ! Tick()
      context.become(WaitTock(items.length))
  }

  def WaitTock(tocksToWait: Int): Receive = {
    case Tock() =>
      if (tocksToWait > 1) {
      //  println("Tock")
        context.become(WaitTock(tocksToWait - 1))
      }
      else {
        //println("Tock")
        //println("Ticking!")
        for (item <- items) item ! Tick()
        context.become(WaitTock(items.length))
      }
  }
}
