package com.logicovercode.fsbt.commons
package services

import com.github.dockerjava.api.model.Capability
import com.logicovercode.docker.cluster._
import com.logicovercode.docker.db.MySqlDbDefinition
import com.logicovercode.docker.hdfs.ClusterNodeHdfsExtension
import com.logicovercode.docker.hive.ClusterNodeHiveExtension
import com.logicovercode.docker.kafka.ClusterNodeKafkaExtension
import com.logicovercode.docker.spark.ClusterNodeSparkExtension
import com.logicovercode.docker.ssh.{ClusterNodeRootSshExtension, ClusterNodeSshExtension}
import com.logicovercode.wdocker.HostConfig

import scala.concurrent.duration.DurationInt

trait MicroServicesProvider extends ClusterBuilderDefinitions {
  object CreateRootSshMicroService extends ClusterNodeRootSshExtension {
    def rootSshMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.sshNodeDefinition(),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSeq = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(
          slaveNode.sshNodeDefinition(),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val allDescriptions = Seq(masterNodeDescription) ++ slaveDescriptionSeq
      MicroService(allDescriptions: _*)
    }
  }

  object CreateSshMicroService extends ClusterNodeSshExtension {
    def sshMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.sshNodeDefinition(),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(
          slaveNode.sshNodeDefinition(),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val allDescriptions = Seq(masterNodeDescription) ++ slaveDescriptionSet
      MicroService(allDescriptions: _*)
    }
  }

  object CreateKafkaMicroService extends ClusterNodeKafkaExtension {
    def kafkaMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.kafkaNodeDefinition(),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(
          slaveNode.kafkaNodeDefinition(),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val allDescriptions = Seq(masterNodeDescription) ++ slaveDescriptionSet
      MicroService(allDescriptions: _*)
    }
  }

  object CreateHdfsMicroService extends ClusterNodeHdfsExtension {
    def hdfsMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.hdfsMasterNodeDefinition(Option(dataNodeHttpPort)),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(
          slaveNode.hdfsSlaveNodeDefinition(Option(dataNodeHttpPort)),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val allDescriptions = Seq(masterNodeDescription) ++ slaveDescriptionSet
      MicroService(allDescriptions: _*)
    }
  }

  object CreateSparkMicroService extends ClusterNodeSparkExtension {
    def sparkMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      val mysqlIp = cluster.lastUsedIp()

      val sysNiceHostConfig = HostConfig(capabilities = Option(Seq(Capability.SYS_NICE)))
      val mysqlContainer = MySqlDbDefinition(
        "hive-mysql-db",
        "metastore",
        "Root@123",
        "hive",
        "hivepswd",
        4444,
        3306,
        cluster.dockerNetwork
      ).withIp(mysqlIp).withHostConfig(sysNiceHostConfig)

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.sparkMasterNodeDefinition(Option(dataNodeHttpPort)),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(
          slaveNode.sparkWorkerNodeDefinition(Option(dataNodeHttpPort)),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val mysqlSbtServiceDescription =
        SbtServiceDescription(mysqlContainer, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      val allDescriptions = Seq(masterNodeDescription) ++ Seq(mysqlSbtServiceDescription) ++ slaveDescriptionSet
      MicroService(allDescriptions: _*)
    }
  }

  object CreateHiveMicroService extends ClusterNodeHiveExtension {
    def hiveMicroService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {

      val mysqlIp = cluster.lastUsedIp()

      val sysNiceHostConfig = HostConfig(capabilities = Option(Seq(Capability.SYS_NICE)))
      val mysqlContainer = MySqlDbDefinition(
        "hive-mysql-db",
        "metastore",
        "Root@123",
        "hive",
        "hivepswd",
        4444,
        3306,
        cluster.dockerNetwork
      ).withIp(mysqlIp).withHostConfig(sysNiceHostConfig)

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(
        cluster.masterNode.hdfsMasterNodeDefinition(Option(dataNodeHttpPort)),
        imagePullTimeoutInMinutes.minutes,
        containerStartTimeoutInMinutes.minutes
      )

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(
          slaveNode.hdfsSlaveNodeDefinition(Option(dataNodeHttpPort)),
          imagePullTimeoutInMinutes.minutes,
          containerStartTimeoutInMinutes.minutes
        )
      }

      val mysqlSbtServiceDescription =
        SbtServiceDescription(mysqlContainer, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      val allDescriptions = Seq(masterNodeDescription) ++ Seq(mysqlSbtServiceDescription) ++ slaveDescriptionSet
      MicroService(allDescriptions: _*)
    }
  }
}
