import sbt._

object Dependencies {

  val finatraVersion = "2.11.0"
  val mockitoVersion = "1.9.5"
  val json4sVersion = "3.5.0"
  val postgresVersion = "9.4.1212"
  val h2Version = "1.4.194"
  val kafkaVersion = "0.10.2.1"
  val slickVersion = "3.2.0"
  val slickHickariVersion = "3.2.0"
  val scalaTestVersion = "3.0.1"
  val guiceVersion = "4.0"
  val akkaHttpVersion = "10.0.4"
  val akkaVersion = "2.5.3"
  val emailVersion="1.4"

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Compile)

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Provided)

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Test)

  def testClassifierDependencies(deps: List[ModuleID]) = deps map (_ % "test" classifier "tests")


  def akkaHttp = Def.setting {
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion :: Nil
  }

  def akkaHttpTestKit = Def.setting {
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion :: Nil
  }


  def json4sNative = Def.setting {
    "org.json4s" %% "json4s-native" % json4sVersion :: Nil
  }

  def json4sEx = Def.setting {
    ("org.json4s" %% "json4s-ext" % json4sVersion).exclude("joda-time", "joda-time") :: Nil
  }

  def jodaDate = Def.setting {
    "joda-time" % "joda-time" % "2.9.2" :: Nil
  }

  def typesafeConfig = Def.setting {
    "com.typesafe" % "config" % "1.3.1" :: Nil
  }

  def logback = Def.setting {
    "ch.qos.logback" % "logback-classic" % "1.1.7" :: Nil
  }

  def slf4j = Def.setting {
    "org.slf4j" % "slf4j-api" % "1.7.25" :: Nil
  }

  def log4j = Def.setting {
    "log4j" % "log4j" % "1.2.17" :: Nil
  }


  /**
    * Finatra dependencies
    */
  def finatraHttp = Def.setting {
    List(
      "com.twitter" %% "finatra-http" % finatraVersion/*,
      "com.twitter" %% "finatra-httpclient" % finatraVersion*/
    )
  }

  def finatraTest = Def.setting {
    List(
      "com.twitter" %% "finatra-http" % finatraVersion,
      "com.twitter" %% "inject-core" % finatraVersion,

      "com.twitter" %% "inject-server" % finatraVersion/*,
      "com.twitter" %% "inject-app" % finatraVersion,
      "com.twitter" %% "inject-modules" % finatraVersion*/
    )
  }

  def kafka = Def.setting {
    "org.apache.kafka" %% "kafka" % kafkaVersion :: Nil
  }

  def akka = Def.setting {
    "com.typesafe.akka" %% "akka-actor" % akkaVersion :: Nil
  }

  def akkaTestKit = Def.setting {
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion :: Nil
  }


  /**
    * Database dependencies
    */

  def postgresDB = Def.setting {
    "org.postgresql" % "postgresql" % postgresVersion :: Nil
  }

  def   h2DB = Def.setting {
    "com.h2database" % "h2" % h2Version :: Nil
  }

  def slick = Def.setting {
    "com.typesafe.slick" %% "slick" % slickVersion :: Nil
  }

  def slickHickari = Def.setting {
    "com.typesafe.slick" %% "slick-hikaricp" % slickHickariVersion :: Nil
  }

  def email=Def.setting{
    "javax.mail" % "mail" % emailVersion :: Nil
  }

  /**
    * Test dependencies
    */
  def mockito = Def.setting {
    "org.mockito" % "mockito-core" % mockitoVersion :: Nil
  }

  def scalaTest = Def.setting {
    ("org.scalatest" %% "scalatest" % scalaTestVersion) :: Nil
  }

  def jbCrypt = Def.setting {
    "org.mindrot" % "jbcrypt" % "0.3m" :: Nil
  }

  def twilio = Def.setting {
    ("com.twilio.sdk" % "twilio" % "7.10.0").exclude("javax.xml.bind", "jaxb-api") :: Nil
  }

  def googleGuiceTest = Def.setting {
    "com.google.inject.extensions" % "guice-testlib" % "4.0" :: Nil
  }

  def scalaCheck = Def.setting {
    "org.scalacheck" %% "scalacheck" % "1.13.4" :: Nil
  }

  def specs2Mock = Def.setting {
    "org.specs2" %% "specs2-mock" % "2.4.17" :: Nil
  }

  def slackApi = Def.setting {
    "com.github.gilbertw1" %% "slack-scala-client" % "0.1.8" :: Nil
  }

  def spec2 = Def.setting {
    "org.specs2" %% "specs2" % "3.7" :: Nil
  }

}
