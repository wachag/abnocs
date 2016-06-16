package hu.bme.mit.abnocs.Database
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Flits.schema ++ Messages.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Flits
   *  @param time Database column time SqlType(int4), Default(None)
   *  @param source Database column source SqlType(int4), Default(None)
   *  @param dest Database column dest SqlType(int4), Default(None)
   *  @param channel Database column channel SqlType(int4), Default(None)
   *  @param head Database column head SqlType(bool), Default(None)
   *  @param tail Database column tail SqlType(bool), Default(None)
   *  @param data Database column data SqlType(bpchar), Default(None)
   *  @param sender Database column sender SqlType(varchar), Length(200,true), Default(None) */
  case class FlitsRow(time: Option[Int] = None, source: Option[Int] = None, dest: Option[Int] = None, channel: Option[Int] = None, head: Option[Boolean] = None, tail: Option[Boolean] = None, data: Option[Char] = None, sender: Option[String] = None)
  /** GetResult implicit for fetching FlitsRow objects using plain SQL queries */
  implicit def GetResultFlitsRow(implicit e0: GR[Option[Int]], e1: GR[Option[Boolean]], e2: GR[Option[Char]], e3: GR[Option[String]]): GR[FlitsRow] = GR{
    prs => import prs._
    FlitsRow.tupled((<<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Boolean], <<?[Boolean], <<?[Char], <<?[String]))
  }
  /** Table description of table flits. Objects of this class serve as prototypes for rows in queries. */
  class Flits(_tableTag: Tag) extends Table[FlitsRow](_tableTag, "flits") {
    def * = (time, source, dest, channel, head, tail, data, sender) <> (FlitsRow.tupled, FlitsRow.unapply)

    /** Database column time SqlType(int4), Default(None) */
    val time: Rep[Option[Int]] = column[Option[Int]]("time", O.Default(None))
    /** Database column source SqlType(int4), Default(None) */
    val source: Rep[Option[Int]] = column[Option[Int]]("source", O.Default(None))
    /** Database column dest SqlType(int4), Default(None) */
    val dest: Rep[Option[Int]] = column[Option[Int]]("dest", O.Default(None))
    /** Database column channel SqlType(int4), Default(None) */
    val channel: Rep[Option[Int]] = column[Option[Int]]("channel", O.Default(None))
    /** Database column head SqlType(bool), Default(None) */
    val head: Rep[Option[Boolean]] = column[Option[Boolean]]("head", O.Default(None))
    /** Database column tail SqlType(bool), Default(None) */
    val tail: Rep[Option[Boolean]] = column[Option[Boolean]]("tail", O.Default(None))
    /** Database column data SqlType(bpchar), Default(None) */
    val data: Rep[Option[Char]] = column[Option[Char]]("data", O.Default(None))
    /** Database column sender SqlType(varchar), Length(200,true), Default(None) */
    val sender: Rep[Option[String]] = column[Option[String]]("sender", O.Length(200,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Flits */
  lazy val Flits = new TableQuery(tag => new Flits(tag))

  /** Entity class storing rows of table Messages
   *  @param time Database column time SqlType(int4), Default(None)
   *  @param source Database column source SqlType(int4), Default(None)
   *  @param dest Database column dest SqlType(int4), Default(None)
   *  @param data Database column data SqlType(varchar), Length(200,true), Default(None)
   *  @param sender Database column sender SqlType(varchar), Length(200,true), Default(None) */
  case class MessagesRow(time: Option[Int] = None, source: Option[Int] = None, dest: Option[Int] = None, data: Option[String] = None, sender: Option[String] = None)
  /** GetResult implicit for fetching MessagesRow objects using plain SQL queries */
  implicit def GetResultMessagesRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[MessagesRow] = GR{
    prs => import prs._
    MessagesRow.tupled((<<?[Int], <<?[Int], <<?[Int], <<?[String], <<?[String]))
  }
  /** Table description of table messages. Objects of this class serve as prototypes for rows in queries. */
  class Messages(_tableTag: Tag) extends Table[MessagesRow](_tableTag, "messages") {
    def * = (time, source, dest, data, sender) <> (MessagesRow.tupled, MessagesRow.unapply)

    /** Database column time SqlType(int4), Default(None) */
    val time: Rep[Option[Int]] = column[Option[Int]]("time", O.Default(None))
    /** Database column source SqlType(int4), Default(None) */
    val source: Rep[Option[Int]] = column[Option[Int]]("source", O.Default(None))
    /** Database column dest SqlType(int4), Default(None) */
    val dest: Rep[Option[Int]] = column[Option[Int]]("dest", O.Default(None))
    /** Database column data SqlType(varchar), Length(200,true), Default(None) */
    val data: Rep[Option[String]] = column[Option[String]]("data", O.Length(200,varying=true), O.Default(None))
    /** Database column sender SqlType(varchar), Length(200,true), Default(None) */
    val sender: Rep[Option[String]] = column[Option[String]]("sender", O.Length(200,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Messages */
  lazy val Messages = new TableQuery(tag => new Messages(tag))
}
