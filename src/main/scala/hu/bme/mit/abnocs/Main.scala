package hu.bme.mit.abnocs
import akka.actor._


object Main extends App {
  val system = ActorSystem("ABNOCS")
  val NOC = system.actorOf(Props[TestNOC], name = "noc1")
  NOC ! Start()
}
