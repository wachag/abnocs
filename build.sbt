name := "abnocs"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0-RC1",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)


//libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"
libraryDependencies += "org.jfree" % "jfreechart" % "1.0.14"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "1.0.1"
libraryDependencies += "org.graphstream" % "gs-core" % "1.3"
libraryDependencies += "org.graphstream" % "gs-ui" % "1.3"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4"
)
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.1"

