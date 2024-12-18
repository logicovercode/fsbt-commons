package com.logicovercode.fsbt.commons
package dependencies.spring

import com.logicovercode.fsbt.commons.dependencies.spring.releases.{Spring_3_2_1_Release, Spring_3_4_0_Release}

trait AllSpringBootReleases {

  val springBoot_3_2_1 = new Spring_3_2_1_Release("3.2.1")
  val springBoot_3_4_0 = new Spring_3_4_0_Release("3.4.0")
}
