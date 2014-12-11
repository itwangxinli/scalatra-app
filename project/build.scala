import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object ScalatraWebAppBuild extends Build {
  val Organization = "com.osc"
  val Name = "scalatra web app"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.3.0"

  lazy val project = Project(
    "scalatra-web-app",
    file("."),
    settings = Seq(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*) ++ ScalatraPlugin.scalatraFullSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases +
        "oschina" at " http://maven.oschina.net/content/groups/public/",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion exclude("org.slf4j", "slf4j-api") withSources(),
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion exclude("org.slf4j", "slf4j-api") withSources(),
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.2" % "compile",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "org.squeryl" %% "squeryl" % "0.9.5-7" withSources(),
        "mysql" % "mysql-connector-java" % "5.1.27",
        "com.h2database" % "h2" % "1.3.166",
        "c3p0" % "c3p0" % "0.9.1.2",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.json4s" %% "json4s-jackson" % "3.2.9",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile) { base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty, /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ), /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
