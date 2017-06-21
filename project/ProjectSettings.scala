import sbt.{Def, _}
import sbt.Keys._

object ProjectSettings {

  def BaseProject(name: String): Project = Project(name, file(name))
    .settings(
      Defaults.coreDefaultSettings ++
        Seq(
          organization := "com.knoldus",
          scalaVersion in ThisBuild := "2.12.0",
          version := "1.0.0",
            fork in Test := true,
          fork in IntegrationTest := true,
          parallelExecution in IntegrationTest := false,
          parallelExecution in Test := false,
          scalaSource in Compile := baseDirectory.value / "src",
          resourceDirectory in Compile := baseDirectory.value / "resources",
          projectResolvers
        ))

  def projectResolvers: Def.Setting[Seq[Resolver]] = {
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "Twitter Maven" at "https://maven.twttr.com",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
    )
  }
}
