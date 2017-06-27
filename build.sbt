name := "Ping"

version := "1.0"

scalaVersion := "2.11.8"


import Dependencies._
import ProjectSettings._

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

lazy val api = BaseProject("api").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value/* ++ finatraSwagger.value*/)
    ++ testDependencies(finatraHttpTest.value ++ spec2.value ++ scalaTest.value)
    ++ testClassifierDependencies(finatraHttpTest.value/*spec2.value*/),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val persistence = BaseProject("persistence").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value ++ logback.value ++
    typesafeConfig.value),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
  ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false
)

lazy val slack = BaseProject("slack").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false
)

lazy val mail = BaseProject("mail").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false
)

lazy val twillio = BaseProject("twillio").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false
)

lazy val client = BaseProject("client").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value ++ json4sNative.value ++ logback.value
    ++ typesafeConfig.value ++ postgresDB.value ++ slick.value ++ slickHickari.value)
    ++ testDependencies(h2DB.value ++ scalaTest.value),
  parallelExecution in Test := false
).dependsOn(commonUtil)
