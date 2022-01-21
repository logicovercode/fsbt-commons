package com.logicovercode.fsbt.commons
package dependencies

trait SpringDependencies {

  import FSbtConvertor._

  case class SpringBootVersion(value: String)

  def springCore(version: String = "4.3.2.RELEASE"): FSbtModuleId = {
    "org.springframework" % "spring-context" % version
  }

  val springBoot_2_3_0 = SpringBootVersion("2.3.0.RELEASE")

  @Deprecated
  val springBootVersion_2_3_0 = SpringBootVersion("2.3.0.RELEASE")

  @Deprecated
  val springBootVersion_2_2_5 = SpringBootVersion("2.2.5.RELEASE")

  case class SpringBootDependencies(releaseTag: SpringBootVersion) {

    def starterParent(): FSbtModuleId = {
      ("org.springframework.boot" % "spring-boot-starter-parent" % releaseTag.value).pomOnly()
    }

    def starterWeb(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-web" % releaseTag.value
    }

    def starterDataJpa(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-data-jpa" % releaseTag.value
    }

    def starterSecurity(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-security" % releaseTag.value
    }

    def starterJdbc(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-jdbc" % releaseTag.value
    }

    def starterTest(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-test" % releaseTag.value
    }

    def postgres(): FSbtModuleId = {
      "org.postgresql" % "postgresql" % "42.2.12"
    }

    def flywayCore(): FSbtModuleId = {
      "org.flywaydb" % "flyway-core" % "6.4.1"
    }
  }

  val springCloudGreenWichRelease = "Greenwich.RELEASE"
  val latestSpriingCloudReleaseTag = springCloudGreenWichRelease

  case class SpringCloud(
      releaseTag: SpringBootVersion = springBootVersion_2_2_5
  ) {

    val versionsMap = Map("Greenwich.RELEASE" -> "2.1.0.RELEASE")

    def dependencies(): FSbtModuleId = {
      ("org.springframework.cloud" % "spring-cloud-dependencies" % releaseTag.value).pomOnly()
    }

    def starterOAuth2(): FSbtModuleId = {
      "org.springframework.cloud" % "spring-cloud-starter-oauth2" % versionsMap(
        releaseTag.value
      )
    }

    def starterSecurity(): FSbtModuleId = {
      "org.springframework.cloud" % "spring-cloud-starter-security" % versionsMap(
        releaseTag.value
      )
    }
  }
}
