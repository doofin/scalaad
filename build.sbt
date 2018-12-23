lazy val root = project.in(file("."))
  .aggregate(scalaad, breeze, nd4j)
  .settings(commonSettings: _*)
  .settings(name := "scalaad-root")
  .settings(publish := { })
  .settings(testSettings: _*)

lazy val scalaad = project.in(file("scalaad"))
  .settings(commonSettings ++ commonPublishSettings:_*)
  .settings(name := "scalaad")

lazy val breeze = project.in(file("breeze"))
  .dependsOn(scalaad)
  .settings(commonSettings ++ commonPublishSettings:_*)

lazy val nd4j = project.in(file("nd4j"))
  .dependsOn(scalaad)
  .settings(commonSettings ++ commonPublishSettings:_*)

lazy val example = project.in(file("example"))
  .dependsOn(breeze)
  .dependsOn(nd4j)
  .dependsOn(scalaad)
  .settings(commonSettings:_*)

lazy val commonSettings = Seq(
  organization            :=  "com.kogecoo",
  name                    <<= name("scalaad-" + _),
  version                 :=  "0.0.1-SNAPSHOT",
  scalaVersion            :=  "2.12.7",
  crossScalaVersions      := Seq("2.12.7"),
  scalacOptions           ++= commonScalacOptions,
  resolvers               ++= commonResolvers,
  libraryDependencies     ++= commonLibraryDependencies
)

lazy val commonPublishSettings = Seq(
  licenses                :=  Seq("Apache License Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  homepage                :=  Some(url("http://kogecoo.github.com/scalaad")),
  publishMavenStyle       :=  true,
  publishArtifact in Test :=  false,
  pomIncludeRepository    :=  (_ => false),
  pomExtra                :=  commonPomExtra,
  publishTo               <<= version { (v: String) => choosePublishTo(v) }
)

lazy val commonScalacOptions = Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-Xlint",
  "-Ywarn-dead-code"
)

lazy val commonResolvers = Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype release Repository" at "http://oss.sonatype.org/service/local/staging/deploy/maven2/",
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository/"
)

lazy val commonLibraryDependencies = Seq(
  "org.scalacheck" %% "scalacheck" % "+" % "test",
  "org.scalatest" %% "scalatest" % "+" % "test"
)

lazy val commonPomExtra = {
  <scm>
    <url>git@github.com:kogecoo/scalaad.git</url>
    <connection>scm:git:git@github.com:kogecoo/scalaad.git</connection>
  </scm>
  <developers>
    <developer>
      <id>kogecoo</id>
      <name>kogecoo</name>
      <url>http://kogecoo.github.com</url>
    </developer>
  </developers>
}

lazy val testSettings = Seq(
  testOptions          += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "2"),
  testOptions          += Tests.Argument(TestFrameworks.ScalaTest, "-oDF")
)

def choosePublishTo(v: String) = {
  if (v.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/temporary")))
  else
    Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/temporary")))
}

