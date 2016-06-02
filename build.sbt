name := "abnocs"

scalaVersion := "2.11.6"

libraryDependencies ++=  Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0-RC1",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
  )
  

//libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"
libraryDependencies += "org.jfree" % "jfreechart" % "1.0.14"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "1.0.1"