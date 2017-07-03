name := "Ping"

version := "1.0"

scalaVersion := "2.11.8"


import Dependencies._
import ProjectSettings._

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

lazy val api =BaseProject("api").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ slf4j.value ++ log4j.value ++ logback.value ++ json4sNative.value ++ json4sEx.value ++
    jodaDate.value ++ kafka.value)
    ++ testDependencies(spec2.value ++ scalaTest.value ++ akkaHttpTestKit.value)
    ++ testClassifierDependencies(Nil),
  parallelExecution in Test := false).dependsOn(commonUtil, persistence)


lazy val persistence = BaseProject("persistence").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value ++
    logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ++ akkaTestKit.value ++ scalaTest.value ++ mockito.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++ finatraHttp.value ++ jbCrypt.value ++ json4sNative.value ++ logback.value ++ typesafeConfig.value ++ kafka.value ++ slf4j.value ++ log4j.value ++ logback.value ++ json4sNative.value ++ json4sEx.value ++
    jodaDate.value)
    ++ testDependencies(h2DB.value ++ mockito.value ++ scalaTest.value ++ spec2.value),

  parallelExecution in Test := false
)


lazy val slack = BaseProject("slack").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ compileDependencies(slackApi.value)
    ++ testDependencies(h2DB.value ++ mockito.value ++ scalaTest.value ++ spec2.value ++ akkaTestKit.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val mail = BaseProject("mail").settings(
  libraryDependencies ++= compileDependencies(json4sNative.value ++ json4sEx.value ++ logback.value ++
    typesafeConfig.value ++ kafka.value ++ email.value ++ akka.value ++ slf4j.value ::: Nil)
    ++ testDependencies(scalaTest.value ++ h2DB.value ++ mockito.value ::: Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val twillio = BaseProject("twillio").settings(
  libraryDependencies ++= compileDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value ++ twilio.value)
    ++ testDependencies(h2DB.value ++ mockito.value ++ scalaTest.value ++ spec2.value ++ akkaTestKit.value),
  parallelExecution in Test := false).dependsOn(commonUtil)


lazy val client = BaseProject("client").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value)
    ++ testDependencies(h2DB.value ++ scalaTest.value ++ googleGuiceTest.value ++
    finatraTest.value ++ scalaCheck.value ++ specs2Mock.value).map(_.exclude("io.netty", "*"))
    ++ testClassifierDependencies(finatraTest.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

