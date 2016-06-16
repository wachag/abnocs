package hu.bme.mit.abnocs.GUI

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import hu.bme.mit.abnocs.Database.DatabaseLogger
import hu.bme.mit.abnocs._
import hu.bme.mit.abnocs.Logger.Logger
import org.graphstream.graph.Graph
import org.jfree.data.UnknownKeyException
import org.jfree.data.category.DefaultCategoryDataset

import scala.collection.immutable.HashMap
import scala.util.Random

/**
  * Created by wachag on 2016.06.02..
  */
trait GUIActor extends Logger {
  var msgCount: Int = 0
  var nocObjects: List[Int] = List()
  val data: DefaultCategoryDataset
  val g: Graph



  def increaseValue(value: String, dest: Int, inc: Int) = {
    try {
      data.incrementValue(inc, value, dest)
    } catch {
      case e: UnknownKeyException => data.addValue(inc, value, dest)
    }

  }
  def updateClockedValues(value:String)= {
    for (i<- nocObjects.indices){
      try{
        data.setValue(nocObjects(i).toDouble/clockCount,value,"CPU"+i)
      }
      catch {
        case e: UnknownKeyException => data.addValue(0, value, "CPU"+i)
      }
    }
  }

  override def log(msg:NOCMsg):NOCMsg = {
    val mesg=super.log(msg)

    mesg match{

      case Tick() => updateClockedValues("Transfer rate: flit/tick")
      case RoutableMessage(source, dest, mg) =>
        msgCount = msgCount + 1
        increaseValue("Complete messages", dest, mg.length())
      case f: Flit =>
        //      increaseValue("Flit", f.dest, 1)
        nocObjects = nocObjects.updated(f.dest,nocObjects(f.dest)+1)
      case AddEdge(s: ActorRef, e: ActorRef) =>
        g.addEdge(s.path.name +
          e.path.name,
          s.path.name,
          e.path.name)
        Unit
      case AddNOCObject(obj: ActorRef) => println(obj.path.name)
        if(obj.path.name.contains("CPU"))
          nocObjects = nocObjects :+ 0
      case m => println(m)
    }
    mesg
  }
}

class GActor (dataA: DefaultCategoryDataset, gA: Graph) extends Logger with GUIActor with DatabaseLogger{
  override val data: DefaultCategoryDataset = dataA
  override val g: Graph = gA
}