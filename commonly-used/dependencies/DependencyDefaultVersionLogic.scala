package com.logicovercode.fsbt.commons
package dependencies

import scala.util.Try

trait DependencyVersionHandler {

  val ALLOW_DEFAULTS = "ALLOW_DEFAULTS_IN_MODULE_ID_CALLS"

  def disableDependenciesWithDefaultVersion(): Unit = {
    System.setProperty(ALLOW_DEFAULTS, "false")
  }

  def enableDependenciesWithDefaultVersion(): Unit = {
    System.setProperty(ALLOW_DEFAULTS, "true")
  }

  def verifyModuleIdVersion(defaultVersion: Version, requestedVersion: String): Boolean = {
    defaultVersion.version.equals(requestedVersion)
  }

  def isDefaultVersionEnabled(): Boolean = {
    val attempt = for {
      v <- Try(sys.props(ALLOW_DEFAULTS))
      b <- Try(v.toBoolean)
    } yield b

    attempt.getOrElse(true)
  }
}

object DependencyDefaultVersionLogic extends DependencyVersionHandler
