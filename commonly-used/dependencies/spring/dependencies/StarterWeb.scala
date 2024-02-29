package com.logicovercode.fsbt.commons
package dependencies.spring.dependencies

import FSbtConvertor._

trait StarterWeb {
  def starterWeb : String
  def starter_web(): FSbtModuleId = {
    ("org.springframework.boot" % "spring-boot-starter-web" % starterWeb)
  }
}