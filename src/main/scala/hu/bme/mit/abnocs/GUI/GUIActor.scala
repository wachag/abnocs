package hu.bme.mit.abnocs.GUI

import akka.actor.Actor
import akka.actor.Actor.Receive
import hu.bme.mit.abnocs.{RoutableMessage, Tick, Tock}
import org.jfree.data.UnknownKeyException
import org.jfree.data.category.DefaultCategoryDataset

import scala.util.Random

/**
  * Created by wachag on 2016.06.02..
  */
class GUIActor(data: DefaultCategoryDataset) extends Actor {
  var msgCount: Int = 0
  var clockCount: Int = 0

  override def receive: Receive = {
    case Tick() => clockCount = clockCount + 1
      sender ! Tock()
    case RoutableMessage(dest, msg) => {
      var x: Number = 0
      msgCount=msgCount+1
      try {
        x = data.getValue("X", dest)
      } catch {
        case e: UnknownKeyException => x = 0
      }
      data.setValue(x.intValue() + 1, "X", dest)
      println(msgCount.toDouble/clockCount.toDouble+ " messages arrived per clock")
    }

  }
}
