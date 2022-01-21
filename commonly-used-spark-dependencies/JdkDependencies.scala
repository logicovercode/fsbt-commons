package com.logicovercode.fsbt.commons
package dependencies

trait JdkDependencies {

  import FSbtConvertor._

  val gson_2_8_6 = Version("2.8.6")
  def gson(version: Version = gson_2_8_6): FSbtModuleId = gson(version.version)
  @Deprecated
  def gson(version: String): FSbtModuleId = "com.google.code.gson" % "gson" % version

  val jwtApi_0_11_2 = Version("0.11.2")
  def jjwt_api(version: Version = jwtApi_0_11_2): FSbtModuleId = jjwt_api(version.version)
  @Deprecated
  def jjwt_api(version: String): FSbtModuleId = "io.jsonwebtoken" % "jjwt-api" % version

  def jjwt_impl(version: Version = jwtApi_0_11_2): FSbtModuleId = jjwt_impl(version.version)
  @Deprecated
  def jjwt_impl(version: String): FSbtModuleId = "io.jsonwebtoken" % "jjwt-impl" % version

  def jjwt_jackson(version: Version = jwtApi_0_11_2): FSbtModuleId = jjwt_jackson(version.version)
  @Deprecated
  def jjwt_jackson(version: String): FSbtModuleId = "io.jsonwebtoken" % "jjwt-jackson" % version

  def jjwt_gson(version: Version = jwtApi_0_11_2): FSbtModuleId = jjwt_gson(version.version)
  @Deprecated
  def jjwt_gson(version: String): FSbtModuleId = "io.jsonwebtoken" % "jjwt-gson" % version

  val vavr_0_10_3 = Version("8.0.23")
  def vavr(version: Version = vavr_0_10_3): FSbtModuleId = vavr(version.version)
  @Deprecated
  def vavr(version: String): FSbtModuleId = "io.vavr" % "vavr" % "0.10.3"

  val mySqlConnector_8_0_23 = Version("8.0.23")
  def mysql_connector_java(version: Version = mySqlConnector_8_0_23): FSbtModuleId = mysql_connector_java(version.version)
  @Deprecated
  def mysql_connector_java(version: String): FSbtModuleId = "mysql" % "mysql-connector-java" % version
}
