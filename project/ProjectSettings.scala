import sbt._
import sbt.Keys._

object ProjectSettings {

  def BaseProject(name: String): Project = Project(name, file(name))
    .settings(
      Defaults.coreDefaultSettings ++
      Seq(
      organization                          := "com.knoldus",
      scalaVersion in ThisBuild             := "2.11.7",
      version 			                        := "1.0.0",
//      scapegoatVersion                      := "1.1.0",
      fork in Test 			                    := true,
      fork in IntegrationTest 			        := true,
      parallelExecution in IntegrationTest 	:= false,
      parallelExecution in Test 	          := false,
//      test in assembly                      := {},
      resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    ))
}
