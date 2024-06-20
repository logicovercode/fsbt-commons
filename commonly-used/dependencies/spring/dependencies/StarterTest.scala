package com.logicovercode.fsbt.commons
package dependencies.spring.dependencies

import FSbtConvertor._

trait StarterTest {
  def starterTest : String
  def starter_test(): FSbtModuleId = {
    ("org.springframework.boot" % "spring-boot-starter-test" % starterTest)
  }
}