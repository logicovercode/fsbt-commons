package com.logicovercode.base_plugin.spark

import sbt.{ExclusionRule, MavenRepository, _}

trait SparkDeprecatedDeps {
  def sparkutils(version: String): ModuleID = {
    "org.mogli" %% "sparkutils" % version
  }

  def sparkcore(
      version: String,
      packageConfiguration: Configuration = Provided
  ): ModuleID = {
    "org.apache.spark" %% "spark-core" % version % packageConfiguration
  }

  def sparksql(
      version: String,
      packageConfiguration: Configuration = Provided
  ): ModuleID = {
    "org.apache.spark" %% "spark-sql" % version % packageConfiguration
  }

  def sparkhive(
      version: String,
      packageConfiguration: Configuration = Provided
  ): ModuleID = {
    "org.apache.spark" %% "spark-hive" % version % packageConfiguration
  }

  def sparkstreaming(
      version: String,
      packageConfiguration: Configuration = Provided
  ): ModuleID = {
    "org.apache.spark" %% "spark-streaming" % version % packageConfiguration
  }

  def sparkIdeResolvers(clusterVariant: String): Set[MavenRepository] = {

    (isInsideIde, clusterVariant) match {
      case (true, "2.1.0.cloudera4") =>
        Set("ICM Repository" at "http://maven.icm.edu.pl/artifactory/repo/")
      case (true, "2.3.1") => Set()
      case (true, _)       => Set()
      case (false, _)      => Set()
    }
  }

  def sparkIdeDependencies(clusterVariant: String): Seq[ModuleID] = {

    (isInsideIde, clusterVariant) match {
      case (true, "2.1.0.cloudera4") =>
        println("inside ide, returning dependencies for 2.1.0.cloudera4")
        Seq(
          "org.apache.spark" %% "spark-launcher" % "2.1.0.cloudera4",
          "org.apache.spark" %% "spark-yarn" % "2.1.0.cloudera4",
          "org.apache.hadoop" % "hadoop-client" % "2.6.0-cdh5.13.0" excludeAll ExclusionRule(
            organization = "javax.servlet"
          )
        )

      case (true, "2.3.1") =>
        println("inside ide, returning dependencies for 2.3.1")
        Seq(
          "org.apache.spark" %% "spark-launcher" % clusterVariant,
          "org.apache.spark" %% "spark-yarn" % clusterVariant,
          "org.apache.hadoop" % "hadoop-client" % "2.7.3" excludeAll ExclusionRule(
            organization = "javax.servlet"
          )
        )
      case (true, cv) =>
        println(
          "inside ide, returning empty dependencies as, cluster variant >" + cv + "< not supported"
        )
        Seq()
      case (false, _) =>
        println("outside ide, returning empty dependencies")
        Seq()
    }
  }

  private def isInsideIde: Boolean = {
    val sparkSqlScope = sys.env.get("SPARK_SQL_SCOPE").getOrElse("compile")
    val sparkStreamingScope =
      sys.env.get("SPARK_STREAMING_SCOPE").getOrElse("compile")
    val sparkHiveScope = sys.env.get("SPARK_HIVE_SCOPE").getOrElse("compile")

    (sparkSqlScope, sparkStreamingScope, sparkHiveScope) match {

      case ("compile", "compile", "compile") =>
        println("inside ide")
        true
      case _ =>
        println("outside ide")
        false
    }
  }
}
