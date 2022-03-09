package com.logicovercode.fsbt.commons
package dependencies

trait SpringDependencies {

  import FSbtConvertor._

  case class SpringBootVersion(value: String)

  trait StarterVersion {
    val releaseTag: SpringBootVersion
  }

  trait StarterParent extends StarterVersion {
    def starter_parent(): FSbtModuleId = {
      ("org.springframework.boot" % "spring-boot-starter-parent" % releaseTag.value).pomOnly()
    }
  }

  trait StarterWeb extends StarterVersion {
    def starter_web(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-web" % releaseTag.value
    }
  }

  trait StarterDataJpa extends StarterVersion {
    def starter_data_jpa(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-data-jpa" % releaseTag.value
    }
  }

  trait StarterSecurity extends StarterVersion {
    def starter_security(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-security" % releaseTag.value
    }
  }

  trait StarterJdbc extends StarterVersion {
    def starter_jdbc(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-jdbc" % releaseTag.value
    }
  }

  trait StarterTest extends StarterVersion {
    def starter_test(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-test" % releaseTag.value
    }
  }

  trait StarterActuator extends StarterVersion {
    def starter_actuator(): FSbtModuleId = {
      "org.springframework.boot" % "spring-boot-starter-actuator" % releaseTag.value
    }
  }

  val springBoot_2_6_4 = new StarterParent
    with StarterWeb
    with StarterDataJpa
    with StarterSecurity
    with StarterJdbc
    with StarterTest
    with StarterActuator {
    override val releaseTag: SpringBootVersion = SpringBootVersion("2.6.4")
  }

  val springBootLatestStableRelease = springBoot_2_6_4
}
