package com.logicovercode.fsbt.commons
package dependencies.spring.dependencies

import FSbtConvertor._

trait StarterParent {
  def starterParent : String
  def starter_parent(): FSbtModuleId = {
    ("org.springframework.boot" % "spring-boot-starter-parent" % starterParent).pomOnly()
  }
}