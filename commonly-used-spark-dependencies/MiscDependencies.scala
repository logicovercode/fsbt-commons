package com.logicovercode.fsbt.commons
package dependencies

trait MiscDependencies {

  import FSbtConvertor._

  val catsEffect_2_3_1 = Version("2.3.1")
  def cats_effect(version: Version = catsEffect_2_3_1): FSbtModuleId = cats_effect(version.version)
  @Deprecated
  def cats_effect(version: String): FSbtModuleId = "org.typelevel" %% "cats-effect" % version


  val scalaJava8Compat_0_9_1 = Version("0.9.1")
  def scala_java8_compat(version: Version = scalaJava8Compat_0_9_1): FSbtModuleId = scala_java8_compat(version.version)
  @Deprecated
  def scala_java8_compat(version: String): FSbtModuleId = "org.scala-lang.modules" %% "scala-java8-compat" % version

  val playJson_2_9_2 = Version("2.9.2")
  def play_json(version: Version = playJson_2_9_2): FSbtModuleId = play_json(version.version)
  @Deprecated
  def play_json(version: String): FSbtModuleId = "com.typesafe.play" %% "play-json" % version

  val logbackClassic_1_2_3 = Version("1.2.3")
  def logback_classic(version: Version = logbackClassic_1_2_3): FSbtModuleId = logback_classic(version.version)
  @Deprecated
  def logback_classic(version: String): FSbtModuleId = "ch.qos.logback" % "logback-classic" % version


  lazy val akkaVersion = "2.6.15"
  def akkaactor(version: String = akkaVersion): FSbtModuleId = {
    "com.typesafe.akka" %% "akka-actor" % version
  }

  def akkacluster(version: String = akkaVersion): FSbtModuleId = {
    "com.typesafe.akka" %% "akka-cluster" % version
  }

  def akkatest(version: String = akkaVersion): FSbtModuleId = {
    "com.typesafe.akka" %% "akka-testkit" % version
  }

  def akkastream(version: String = akkaVersion): FSbtModuleId = {
    "com.typesafe.akka" %% "akka-stream" % version
  }

  @Deprecated
  def akkaclient(version: String = "2.4.17"): FSbtModuleId = {
    "com.typesafe.akka" %% "akka-cluster-tools" % version
  }

  @Deprecated
  def typesafeConfig(version: String = "1.3.4"): FSbtModuleId = {
    "com.typesafe" % "config" % version
  }

  @Deprecated
  def junit(version: String = "default"): FSbtModuleId = {
    "junit" % "junit" % "4.12"
    //    Seq("org.hamcrest" % "hamcrest-all" % "1.3",
    //      "pl.pragmatists" % "JUnitParams" % "1.0.4",
    //      "junit" % "junit" % "4.12")
  }

  @Deprecated
  def scalaz(version: String = "7.2.30"): FSbtModuleId = {
    "org.scalaz" %% "scalaz-core" % version
  }

  @Deprecated
  def scalalib(version: String = "2.11.7"): FSbtModuleId = {
    "org.scala-lang" % "scala-library" % version
  }

  @Deprecated
  def scalalib12(version: String = "2.12.2"): FSbtModuleId = {
    "org.scala-lang" % "scala-library" % version
  }

  @Deprecated
  def mockito(version: String = "1.8.4"): FSbtModuleId = {
    "org.mockito" % "mockito-all" % version
  }

  @Deprecated
  def easymock(version: String = "3.4"): FSbtModuleId = {
    "org.easymock" % "easymock" % version
  }

  @Deprecated
  def scalamock(version: String = "3.6.0"): FSbtModuleId = {
    "org.scalamock" %% "scalamock-scalatest-support" % version
  }

  @Deprecated
  def guava(version: String = "11.0.2"): FSbtModuleId = {
    "com.google.guava" % "guava" % version
  }

  @Deprecated
  def guice(version: String = "3.0"): FSbtModuleId = {
    "com.google.inject" % "guice" % version
  }

  @Deprecated
  def akkaconfig(version: String = "1.3.3"): FSbtModuleId = {
    "com.typesafe" % "config" % "1.3.3"
  }
}
