import sbt._

object Dependencies {

  val finatraVersion = "2.11.0"
  val mockitoVersion = "1.10.19"
  val json4sVersion = "3.5.0"
  val postgresVersion = "9.4.1208"
  val h2Version = "1.4.194"
  val slickVersion = "3.2.0"
  val slickHickariVersion = "3.2.0"

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def typesafeConfig = Def.setting {
    "com.typesafe" % "config" % "1.3.1" :: Nil
  }

  def logback = Def.setting {
    "ch.qos.logback" % "logback-classic" % "1.1.6" :: Nil
  }

  def finatraHttp = Def.setting {
    "com.twitter" % "finatra-http_2.11" % finatraVersion :: Nil
  }

  def mockito = Def.setting {
    "org.mockito" % "mockito-core" % mockitoVersion :: Nil
  }

  def json4sNative = Def.setting {
    "org.json4s" %% "json4s-native" % json4sVersion :: Nil
  }

  def postgresDB = Def.setting {
    "org.postgresql" % "postgresql" % postgresVersion :: Nil
  }

  def h2DB = Def.setting {
    "com.h2database" % "h2" % h2Version :: Nil
  }

  def slick =Def.setting {
    "com.typesafe.slick" %% "slick" % slickVersion :: Nil
  }

  def slickHickari = Def.setting {
    "com.typesafe.slick" %% "slick-hikaricp" % slickHickariVersion :: Nil
  }

}
