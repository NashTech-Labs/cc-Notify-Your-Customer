import sbt._

object Dependencies {

  val finatraVersion = "2.10.0"
  val mockitoVersion = "1.10.19"
  val json4sVersion = "3.5.0"
  val postgresVersion = "9.4.1208"
  val quillVersion = "1.2.1"
  val h2Version = "1.4.194"
  val swaggerVersion = "2.9.0"
  val kafkaVersion = ""

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

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
    "com.twitter" %% "finatra-http" % finatraVersion :: Nil
  }

  def finatraSwagger = Def.setting {
    "com.jakehschwartz" % "finatra-swagger_2.12" % swaggerVersion :: Nil
  }

  def json4sNative = Def.setting {
    "org.json4s" % "json4s-native_2.11" % json4sVersion :: Nil
  }

  def kafka = Def.setting {
    "org.apache.kafka" % "kafka_2.12" % "0.10.2.1" :: Nil
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

  def quill = Def.setting {
    "io.getquill" %% "quill-async-postgres" % quillVersion :: Nil
  }

  /**
    * Test dependencies
    */
  def mockito = Def.setting {
    "org.mockito" % "mockito-core" % mockitoVersion :: Nil
  }

  def scalaTest = Def.setting {
    "org.scalatest" % "scalatest_2.11" % "2.2.4" :: Nil
  }

  /*def spec2 = Def.setting {
    "org.specs2" %% "specs2" % "2.3.12" :: Nil
  }*/

}
