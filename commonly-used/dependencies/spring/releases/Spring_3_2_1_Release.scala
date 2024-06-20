package com.logicovercode.fsbt.commons

package dependencies.spring.releases

import dependencies.spring.dependencies.{StarterParent, StarterTest, StarterWeb}

case class Spring_3_2_1_Release(releaseVersion : String)
  extends StarterParent
    with StarterWeb
    with StarterTest {

  override def starterParent: String = releaseVersion

  override def starterWeb: String = releaseVersion

  override def starterTest: String = releaseVersion
}