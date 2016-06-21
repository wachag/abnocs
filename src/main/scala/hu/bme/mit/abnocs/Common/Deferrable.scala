package hu.bme.mit.abnocs.Common

import akka.actor.{Actor, ActorRef}

/**
  * Created by wachag on 2016.06.21..
  */
trait Deferrable extends Actor{
  var works:List[WorkItem] = List()

  def defer(item:WorkItem):Unit= works=works:+item
  def executeDeferred(whereTo:ActorRef):Unit={
    works.foreach(w => {whereTo ! w})
    works=List()
  }

}
