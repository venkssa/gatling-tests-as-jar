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

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)


enablePlugins(GatlingPlugin, AssemblyPlugin)
Project.inConfig(IntegrationTest)(baseAssemblySettings)
assemblyJarName in (IntegrationTest, assembly) := s"${name.value}-test-${version.value}.jar"
mainClass in (IntegrationTest, assembly) := Some("io.gatling.app.Gatling")
assemblyMergeStrategy in (IntegrationTest, assembly) := {
  case x if x.contains("io.netty.versions.properties")  => MergeStrategy.rename
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}


val playVersion              = "2.5.4"
val scalaCheckVersion        = "1.12.2"
val typeSafeConfigVersion    = "1.3.0"
val jsoupVersion             = "1.9.2"
val gatlingVersion           = "2.2.3"

libraryDependencies ++= Seq(
  "com.typesafe.play"        %%  "play-json"                 % playVersion           % IntegrationTest,
  "org.scalacheck"           %%  "scalacheck"                % scalaCheckVersion     % IntegrationTest,
  "com.typesafe"              %  "config"                    % typeSafeConfigVersion % IntegrationTest,
  "org.jsoup"                 %  "jsoup"                     % jsoupVersion          % IntegrationTest,
  "io.gatling.highcharts"     %  "gatling-charts-highcharts" % gatlingVersion        % IntegrationTest,
  "io.gatling"                %  "gatling-test-framework"    % gatlingVersion        % IntegrationTest
)
