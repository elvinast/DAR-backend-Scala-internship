name := "test"

version := "0.1"

scalaVersion := "2.13.4"


val AkkaVersion = "2.6.10"
val AkkaPersistenceCassandraVersion = "1.0.3"
val AkkaHttpVersion = "10.2.1"
val AkkaProjectionVersion = "1.0.0"
val circeVersion = "0.13.0"
val swaggerVersion = "2.1.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % "Test",
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % "Test",
  "joda-time" % "joda-time" % "2.10.5",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"
)
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion