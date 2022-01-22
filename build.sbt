import com.logicovercode.bsbt.scala_module.ScalaBuild

val githubRepo = githubHosting("logicovercode", "fsbt-commons", "techLeadAtLogicOverCode", "techlead@logicovercode.com")

val sbtBuild = ScalaBuild("com.logicovercode", "fsbt-commons", "0.0.002")
  .sourceDirectories("commonly-used", "generated-dependencies")
  .testSourceDirectories("dependency-generator")
  .dependencies(
    "com.logicovercode" %% "docker-definitions" % "0.0.007",
    "com.logicovercode" %% "fsbt-adts" % "0.0.001"
  )
  .testDependencies(
    "org.scalatest" %% "scalatest" % "3.2.10",
    "org.scalameta" %% "scalameta" % "4.4.32",
    "com.github.pathikrit" %% "better-files" % "3.9.1"
  )
  .testResourceDirectories("dependency-generator-config")
  .scalaVersions(scala_2_13_MaxVersion, Seq(scala_2_13_MaxVersion, scala_2_12_MaxVersion))
  .javaCompatibility("1.8", "1.8")
  .publish(githubRepo.developer, MIT_License, githubRepo, Opts.resolver.sonatypeStaging)

idePackagePrefix := Some("com.logicovercode.fsbt.commons")

//val fSbtAdtsModule = SbtModule("com.logicovercode" %% "fsbt-adts" % "0.0.001", PARENT_DIRECTORY / "fsbt-adts", "fSbtAdtsProject")

lazy val fSbtCommonsProject = (project in file("."))
  .settings(sbtBuild.settings)
  //.dependsOn(fSbtAdtsModule)
