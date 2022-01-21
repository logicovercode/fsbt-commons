package com.logicovercode.fsbt.commons
package services

import com.logicovercode.wdocker.{ContainerDefinition, DockerNetwork, DockerReadyChecker}

import scala.concurrent.duration.DurationInt

trait PostgresSqlServiceProvider {
  object PostgresService {

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        dbInitDirectory: String
    ): SbtMicroservice = {

      postgresSbtServiceDescription(
        containerName,
        databaseName,
        userName,
        dbPassword,
        5432,
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
    ): SbtMicroservice = {

      postgresSbtServiceDescription(
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

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String): SbtMicroservice = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, 5432, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int): SbtMicroservice = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork
    ): SbtMicroservice = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, network, None, 5, 5)
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
    ): SbtMicroservice = {

      postgresSbtServiceDescription(
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

    private def postgresSbtServiceDescription(
        containerName: String,
        databaseName: String,
        dbUserName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork,
        dbInitDirectory: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): SbtMicroservice = {
      val imageName: String = "postgres:latest"
      val dbContainerPort = 5432

      val dbContainer = ContainerDefinition(None, "postgres", "latest", Option(containerName))
        .withEnv(
          s"POSTGRES_DB=$databaseName",
          s"POSTGRES_USER=$dbUserName",
          s"POSTGRES_PASSWORD=$dbPassword"
        )
        .withPorts(dbContainerPort -> Some(hostPort))
        .withNetworkMode(network)
        .withReadyChecker(
          DockerReadyChecker.LogLineContains(
            s"database system is ready to accept connections"
          )
        )

      val dir = dbInitDirectory match {
        case Some(dir) => Seq(s"filesystem:$dir")
        case None      => Seq()
      }

      val host = "localhost"
      val url = s"jdbc:mysql://${host}:$hostPort/$databaseName"

      val flywayConfig = SbtFlywayConfig(url, dbUserName, dbPassword, Seq(s"filesystem:$dir"))

      val sbtServiceDescription =
        SbtServiceDescription(dbContainer, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      DbService(sbtServiceDescription, flywayConfig)
    }
  }
}
