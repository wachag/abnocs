package hu.bme.mit.abnocs.Database
import hu.bme.mit.abnocs.Common.{Flit, NOCMsg, RoutableMessage}
import hu.bme.mit.abnocs.Logger._
import slick.driver.PostgresDriver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.duration.Duration
/**
  * Created by wachag on 2016.06.16..
  */
trait DatabaseLogger extends Logger{

  val db= Database.forConfig("akne")

  val fdrop=DBIO.seq(
    Tables.Flits.schema.drop
  )
  val fcreate=DBIO.seq(
    Tables.Flits.schema.create
  )
  try {
    Await.result(db.run(fdrop), Duration.Inf)
  }
  catch{case e:Throwable=>}
  try {
    Await.result(db.run(fcreate), Duration.Inf)
  }
  catch{case e:Throwable=>}

  override def log(msg: NOCMsg): NOCMsg = {
    val me:NOCMsg=super.log(msg)

    me match {
      case f:Flit =>
        val d=DBIO.seq(Tables.Flits += Tables.FlitsRow(Some(clockCount),Some(f.source),Some(f.dest),Some(f.channel),Some(f.head),Some(f.tail),Some(f.data),Some(sender.path.name)))
        Await.result(db.run(d),Duration.Inf)
      case r:RoutableMessage =>
        val d=DBIO.seq(Tables.Messages += Tables.MessagesRow(Some(clockCount),Some(r.source),Some(r.dest),Some(r.payload),Some(sender.path.name)))
        Await.result(db.run(d),Duration.Inf)
      case m =>
    }
    me
  }
}
