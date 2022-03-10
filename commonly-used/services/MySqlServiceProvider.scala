package com.logicovercode.fsbt.commons
package services

import com.logicovercode.docker.db.MySqlDbDefinition
import com.logicovercode.wdocker.DockerNetwork

import scala.concurrent.duration.DurationInt

trait MySqlServiceProvider {
  object MySqlService {

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        dbInitDirectory: String
    ): SbtService = {

      mysqlSbtServiceDescription(
        containerName,
        databaseName,
        userName,
        dbPassword,
        3306,
        DockerNetwork("bridge"),
        Option(dbInitDirectory),
        5,
        5
      )
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        dbInitDirectory: String
    ): SbtService = {

      mysqlSbtServiceDescription(
        containerName,
        databaseName,
        userName,
        dbPassword,
        hostPort,
        DockerNetwork("bridge"),
        Option(dbInitDirectory),
        5,
        5
      )
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String): SbtService = {

      mysqlSbtServiceDescription(containerName, databaseName, userName, dbPassword, 3306, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int): SbtService = {

      mysqlSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork
    ): SbtService = {

      mysqlSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, network, None, 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork,
        dbIntDir: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): SbtService = {

      mysqlSbtServiceDescription(
        containerName,
        databaseName,
        userName,
        dbPassword,
        hostPort,
        network,
        dbIntDir,
        imagePullTimeoutInMinutes,
        containerStartTimeoutInMinutes
      )
    }

    def mysqlSbtServiceDescription(
        containerName: String,
        databaseName: String,
        dbUserName: String,
        dbPassword: String,
        hostPort: Int,
        sqlDockerNetwork: DockerNetwork,
        dbInitDirectory: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): SbtService = {

      val rootUserPassword = "RootPass@123"

      val mysqlContainer = MySqlDbDefinition(
        containerName,
        databaseName,
        rootUserPassword,
        dbUserName,
        dbPassword,
        hostPort,
        3306,
        sqlDockerNetwork
      )

      val dir = dbInitDirectory match {
        case Some(dir) => Seq(s"filesystem:$dir")
        case None      => Seq()
      }

      val host = "localhost"
      val url = s"jdbc:mysql://${host}:$hostPort/$databaseName"

      val flywayConfig = SbtFlywayConfig(url, dbUserName, dbPassword, Seq(s"filesystem:$dir"))

      val sbtServiceDescription =
        SbtServiceDescription(mysqlContainer, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      DbService(sbtServiceDescription, flywayConfig)
    }
  }
}
