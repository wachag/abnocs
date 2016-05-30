package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.{AddNOCObject, NOCMsg, RoutableMessage}

import scala.util.Random

/**
  * Created by wachag on 2016.05.30..
  */
trait RandomCPUGenerator {
  val numCPUs: Int
  val context: ActorContext
  val messageProbability: Double

  def generateCPU(router: ActorRef): ActorRef = {
    val cpu0: ActorRef = context.actorOf(Props(new RandomCPU(router, numCPUs, messageProbability)))
    cpu0 ! AddNOCObject(router)
    router ! AddNOCObject(cpu0)
    cpu0
  }
}

trait RandomDestinationProcessor extends CPU {
  val numCPUs: Int = 9

  override def generateMessage(): Option[NOCMsg] = {
    super.generateMessage() match {
      case None => Some(RoutableMessage(Random.nextInt(numCPUs), "hello"))
      case Some(msg) => Some(msg)
      case _ => None
    }
  }
}

trait RandomProbabilityProcessor extends CPU {
  val messageProbability: Double = 0.1

  override def generateMessage(): Option[NOCMsg] = {
    super.generateMessage() match {
      case None => None
      case Some(msg) =>
        if (Random.nextDouble() < messageProbability) Some(msg)
        else None
      case _ => None
    }
  }
}

class RandomCPU(router: ActorRef, nCPUs: Int, msgProb: Double) extends CPU(router) with RandomDestinationProcessor with RandomProbabilityProcessor with LoggerCPU {
  override val numCPUs: Int = nCPUs
  override val messageProbability: Double = msgProb
}