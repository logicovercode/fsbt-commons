package com.logicovercode.fsbt.commons
package dependencies

trait DoobieDependencies {

  import FSbtConvertor._

  val http4s_0_21_16 = Version("0.21.16")

  case class Http4sDependencies(version: Version = http4s_0_21_16) {
    def http4s_dsl(): FSbtModuleId = "org.http4s" %% "http4s-dsl" % version.version
    def http4s_blaze_server(): FSbtModuleId = "org.http4s" %% "http4s-blaze-server" % version.version
  }

  final val doobie_0_12_1 = Version("0.12.1")

  case class DoobieDependencies(version: Version = doobie_0_12_1) {
    def core(): FSbtModuleId = {
      "org.tpolecat" %% "doobie-core" % version.version
    }

    def specs2(): FSbtModuleId = {
      "org.tpolecat" %% "doobie-specs2" % version.version
    }

    def postgres(): FSbtModuleId = {
      "org.tpolecat" %% "doobie-postgres" % version.version
    }
  }
}
