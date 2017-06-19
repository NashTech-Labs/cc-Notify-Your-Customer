name := "Ping"

version := "1.0"

scalaVersion := "2.12.2"

import Dependencies._
import ProjectSettings._

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

lazy val api = BaseProject("api").settings(
  libraryDependencies ++= compileDependencies(finatraHttp.value ++ finatraSwagger.value)
    ++ testDependencies(/*spec2.value*/Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    "Twitter Maven" at "https://maven.twttr.com")).dependsOn(commonUtil)

lazy val persistence = BaseProject("persistence").settings(
  libraryDependencies ++= compileDependencies(postgresDB.value ++ quill.value ++ logback.value ++
    typesafeConfig.value),
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

lazy val slack = BaseProject("slack").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")
)

lazy val mail = BaseProject("mail").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")
)

lazy val twillio = BaseProject("twillio").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")
)

lazy val partner = BaseProject("partner").settings(
  libraryDependencies ++= providedDependencies(json4sNative.value ++ logback.value ++ typesafeConfig.value)
    ++ testDependencies(h2DB.value ::: Nil),
  parallelExecution in Test := false,
  resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")
)
