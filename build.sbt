name := "Ping"

version := "1.0"

scalaVersion := "2.12.2"

import Dependencies._
import ProjectSettings._


lazy val api = BaseProject("api").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value ++ json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")).dependsOn(commonUtil)

lazy val persistence = BaseProject("persistence").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ slick.value ++ slickHickari.value ++ logback.value ++ typesafeConfig.value),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")).dependsOn(commonUtil)

lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
  ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")
)
