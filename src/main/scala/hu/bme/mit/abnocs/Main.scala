package hu.bme.mit.abnocs
import akka.actor._


object Main extends App {
  println("Start1");
  val system = ActorSystem("ABNOCS")
  println("Start2");
  val NOC = system.actorOf(Props[NOC], name = "noc1")
  NOC ! Start()
  println("Start6");
}
