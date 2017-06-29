name := "Ping"

version := "1.0"

scalaVersion := "2.11.8"


import Dependencies.{scalaTest, _}
import ProjectSettings._


lazy val api = BaseProject("api").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value)
    ++ testDependencies(/*spec2.value*/ Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val persistence = BaseProject("persistence").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value ++
    logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ++ akkaTestKit.value ++ scalaTest.value ++ mockito.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value ++ json4sNative.value ++ logback.value ++ typesafeConfig.value ++ jbCrypt.value)
    ++ testDependencies(h2DB.value ++ scalaTest.value),
  parallelExecution in Test := false
)


lazy val slack = BaseProject("slack").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value  ++ typesafeConfig.value)
    ++ compileDependencies(slackApi.value)
    ++ testDependencies(h2DB.value ++ mockito.value ++ scalaTest.value ++ spec2.value),
  parallelExecution in Test := false
)

lazy val mail = BaseProject("mail").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val twillio = BaseProject("twillio").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value ++
    kafka.value ++ akka.value)
    ++ testDependencies(h2DB.value ++ akkaTestKit.value ++ scalaTest.value ++ mockito.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val client = BaseProject("client").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value)
    ++ testDependencies(h2DB.value ++ scalaTest.value ++ googleGuiceTest.value ++
    finatraTest.value ++ scalaCheck.value ++ specs2Mock.value).map(_.exclude("io.netty", "*"))
    ++ testClassifierDependencies(finatraTest.value),
  parallelExecution in Test := false).dependsOn(commonUtil)
