import io.gatling.sbt.GatlingPlugin

name := "gatling-tests-as-jar"

version := "1.0"

scalaVersion := "2.11.8"

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

parallelExecution in Test := false

enablePlugins(GatlingPlugin, AssemblyPlugin)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)

Project.inConfig(IntegrationTest)(baseAssemblySettings)
assemblyJarName in (IntegrationTest, assembly) := s"${name.value}-${version.value}.jar"
mainClass in assembly := Some("example.Cli")

val playVersion              = "2.4.3"
val scalaCheckVersion        = "1.12.2"
val typeSafeConfigVersion    = "1.3.0"
val jsoupVersion             = "1.9.2"
val gatlingVersion           = "2.1.7"

libraryDependencies ++= Seq(
  "com.typesafe.play"        %%  "play-json"                 % playVersion           % IntegrationTest,
  "org.scalacheck"           %%  "scalacheck"                % scalaCheckVersion     % IntegrationTest,
  "com.typesafe"              %  "config"                    % typeSafeConfigVersion % IntegrationTest,
  "org.jsoup"                 %  "jsoup"                     % jsoupVersion          % IntegrationTest,
  "io.gatling.highcharts"     %  "gatling-charts-highcharts" % gatlingVersion        % IntegrationTest,
  "io.gatling"                %  "gatling-test-framework"    % gatlingVersion        % IntegrationTest
)
