import sbt._

object Dependencies {

  val finatraVersion = "2.11.0"
  val mockitoVersion = "1.10.19"
  val json4sVersion = "3.5.0"
  val postgresVersion = "9.4.1212"
  val h2Version = "1.4.194"
  val swaggerVersion = "2.9.0"
  val kafkaVersion = "0.10.2.1"
  val slickVersion = "3.2.0"
  val slickHickariVersion = "3.2.0"
  val scalaTestVersion = "3.0.1"
  val akkaVersion = "2.5.3"

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Compile)

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Provided)

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Test)

  def testClassifierDependencies(deps: List[ModuleID]) = deps map (_ % "test" classifier "tests")

  def typesafeConfig = Def.setting {
    "com.typesafe" % "config" % "1.3.1" :: Nil
  }

  def logback = Def.setting {
    "ch.qos.logback" % "logback-classic" % "1.1.6" :: Nil
  }

  /**
    * Finatra dependencies
    */
  def finatraHttp = Def.setting {
    List(
      "com.twitter" %% "inject-server" % finatraVersion,
      "com.twitter" %% "inject-app" % finatraVersion,
      "com.twitter" %% "inject-core" % finatraVersion,
      "com.twitter" %% "inject-modules" % finatraVersion,
      "com.twitter" %% "finatra-http" % finatraVersion,
      "com.twitter" %% "finatra-httpclient" % finatraVersion
    )
  }

  def finatraTest = Def.setting {
    List(
      "com.twitter" %% "finatra-http" % finatraVersion,
      "com.twitter" %% "inject-server" % finatraVersion,
      "com.twitter" %% "inject-app" % finatraVersion,
      "com.twitter" %% "inject-core" % finatraVersion,
      "com.twitter" %% "inject-modules" % finatraVersion
    )
  }

  def finatraSwagger = Def.setting {
    "com.jakehschwartz" %% "finatra-swagger" % swaggerVersion :: Nil
  }

  def json4sNative = Def.setting {
    "org.json4s" %% "json4s-native" % json4sVersion :: Nil
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

  def h2DB = Def.setting {
    "com.h2database" % "h2" % h2Version :: Nil
  }

  def slick = Def.setting {
    "com.typesafe.slick" %% "slick" % slickVersion :: Nil
  }

  def slickHickari = Def.setting {
    "com.typesafe.slick" %% "slick-hikaricp" % slickHickariVersion :: Nil
  }

  /**
    * Test dependencies
    */
  def mockito = Def.setting {
    "org.mockito" % "mockito-core" % mockitoVersion :: Nil
  }

  def scalaTest = Def.setting {
    "org.scalatest" %% "scalatest" % scalaTestVersion :: Nil
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

}
