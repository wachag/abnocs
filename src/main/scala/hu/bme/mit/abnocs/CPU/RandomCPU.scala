package hu.bme.mit.abnocs.CPU

import akka.actor.{ActorContext, ActorRef, Props}
import hu.bme.mit.abnocs.Common.{AddNOCObject, NOCMsg, RoutableMessage}

import scala.util.Random

/**
  * Created by wachag on 2016.05.30..
  */
trait RandomCPUGenerator {
  val numCPUs: Int
  val context: ActorContext
  val messageProbability: Double

  def generateCPU(id: Int, router: ActorRef): ActorRef = {
    val cpu0: ActorRef = context.actorOf(Props(new RandomCPU(router, id, numCPUs, messageProbability)), "CPU" + id)
    cpu0 ! AddNOCObject(router)
    router ! AddNOCObject(cpu0)
    cpu0
  }
}

trait RandomDestinationProcessor extends CPU {
  val numCPUs: Int = 9

  override def generateMessage(): Option[NOCMsg] = {
    super.generateMessage() match {
      case None => None
      case Some(RoutableMessage(source, dest, msg)) => Some(RoutableMessage(source, Random.nextInt(numCPUs), msg))
      case Some(msg) => Some(msg)
      case _ => None
    }
  }
}

trait RandomMessageProcessor extends CPU {
  val numCPUs: Int = 9
  val toCPU: Int = 0
  val maxLength: Int = 16

  override def generateMessage(): Option[NOCMsg] = {
    super.generateMessage() match {
      case Some(msg) => Some(msg) /* we forward sane messages */
      case _ => Some(RoutableMessage(cpuId, toCPU, Random.alphanumeric.take(Random.nextInt(maxLength) + 1).mkString)) /* we generate a message on every cycle */
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

class RandomCPU(router: ActorRef, id: Int, nCPUs: Int, msgProb: Double) extends CPU(router, id) with RandomMessageProcessor with RandomDestinationProcessor with RandomProbabilityProcessor with FlitMessageCPU with LoggerCPU {
  override val numCPUs: Int = nCPUs
  override val messageProbability: Double = msgProb
}