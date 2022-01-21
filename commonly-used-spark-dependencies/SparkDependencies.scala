package com.logicovercode.base_plugin.spark

import com.logicovercode.base_plugin.Version
import com.logicovercode.bsbt.module_id.JvmModuleID
import sbt._

trait SparkDependencies {

  val spark_3_1_1 = Version("3.1.1")

  case class SparkDependencies(version : Version = spark_3_1_1){
    def spark_core(): JvmModuleID = {
      JvmModuleID("org.apache.spark" %% "spark-core" % version.version % Provided)
    }

    @Deprecated
    def logging(version: String = "default"): Seq[JvmModuleId] = {
      Seq(
        "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
        "ch.qos.logback" % "logback-classic" % "1.1.2"
      ).map(JvmModuleId(_, None))
    }

    def spark_sql(): JvmModuleID = {
      JvmModuleID("org.apache.spark" %% "spark-sql" % version.version % Provided)
    }

    def spark_hive(): JvmModuleID = {
      JvmModuleID("org.apache.spark" %% "spark-hive" % version.version % Provided)
    }

    def spark_streaming(): JvmModuleID = {
      JvmModuleID("org.apache.spark" %% "spark-streaming" % version.version % Provided)
    }
  }
}
