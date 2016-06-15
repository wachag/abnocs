package hu.bme.mit.abnocs.GUI

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import hu.bme.mit.abnocs._
import org.graphstream.graph.Graph
import org.jfree.data.UnknownKeyException
import org.jfree.data.category.DefaultCategoryDataset

import scala.util.Random

/**
  * Created by wachag on 2016.06.02..
  */
class GUIActor(data: DefaultCategoryDataset, g: Graph) extends Actor {
  var msgCount: Int = 0
  var clockCount: Int = 0
  g.setStrict(false)
  g.setAutoCreate(true)

  def increaseValue(value: String, dest: Int, inc: Int) = {
    try {
      data.incrementValue(inc, value, dest)
    } catch {
      case e: UnknownKeyException => data.addValue(inc, value, dest)
    }

  }

  override def receive: Receive = {
    case Tick() => clockCount = clockCount + 1
      sender ! Tock()
    case RoutableMessage(source, dest, msg) =>
      msgCount = msgCount + 1
      increaseValue("Complete messages", dest, msg.length())
    case m: Flit =>
      increaseValue("Flit", m.dest, 1)
    case AddEdge(s: ActorRef, e: ActorRef) => g.addEdge(s.path.name + e.path.name, s.path.name, e.path.name)
    case msg => println(msg)

  }
}
