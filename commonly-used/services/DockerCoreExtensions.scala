package com.logicovercode.fsbt.commons
package services

import com.logicovercode.wdocker.ContainerDefinition

import scala.concurrent.duration.DurationInt

trait DockerCoreExtensions {

  implicit class ContainerDefinitionExtension(containerDefinition: ContainerDefinition) {
    def configurableAttributes(imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtServiceDescription = {
      val sbtServiceDescription =
        SbtServiceDescription(containerDefinition, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      //MicroService(sbtServiceDescription)
      sbtServiceDescription
    }
  }
}
