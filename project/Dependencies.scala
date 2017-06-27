import sbt._

object Dependencies {

  val finatraVersion = "2.1.2"
  val mockitoVersion = "1.10.19"
  val json4sVersion = "3.5.0"
  val postgresVersion = "9.4.1208"
  val h2Version = "1.4.194"
  val swaggerVersion = "2.9.0"
  val kafkaVersion = "0.10.2.1"
  val slickVersion = "3.2.0"
  val slickHickariVersion = "3.2.0"
  val scalaTestVersion = "3.0.1"
  val logbackVersion = "1.1.3"
  val guiceVersion = "4.0"

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def testClassifierDependencies(deps: List[ModuleID]) = deps map (_ % "test" classifier "tests")


  def typesafeConfig = Def.setting {
    "com.typesafe" % "config" % "1.3.1" :: Nil
  }

  def logback = Def.setting {
    "ch.qos.logback" % "logback-classic" % logbackVersion :: Nil
  }

  /**
    * Finatra dependencies
    */
  def finatraHttp = Def.setting {
    ("com.twitter.finatra" %% "finatra-http" % finatraVersion) :: Nil
  }
  
  def finatraHttpTest = Def.setting{
    List(
      "com.twitter.finatra" %% "finatra-slf4j" % finatraVersion,
      "com.twitter.finatra" %% "finatra-http" % finatraVersion,
      "com.twitter.inject" %% "inject-server" % finatraVersion,
      "com.twitter.inject" %% "inject-app" % finatraVersion,
      "com.twitter.inject" %% "inject-core" % finatraVersion,
      "com.twitter.inject" %% "inject-modules" % finatraVersion,
      "com.google.inject.extensions" % "guice-testlib" % guiceVersion,
      "com.twitter.finatra" %% "finatra-jackson" % finatraVersion

    )
  }

  /*def finatraSwagger = Def.setting {
    "com.jakehschwartz" %% "finatra-swagger" % swaggerVersion :: Nil
  }*/

  def json4sNative = Def.setting {
    "org.json4s" %% "json4s-native" % json4sVersion :: Nil
  }

  def kafka = Def.setting {
    "org.apache.kafka" % "kafka_2.12" % kafkaVersion :: Nil
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

  def spec2 = Def.setting {
    "org.specs2" %% "specs2" % "3.7" :: Nil
  }

}
