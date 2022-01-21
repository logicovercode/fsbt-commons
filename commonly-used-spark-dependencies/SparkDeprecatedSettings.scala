package com.logicovercode.base_plugin.spark

trait SparkDeprecatedSettings {
  def initCommands(): String = {
    val isSparkConsoleApp = sys.env.get("IS_SPARK_CONSOLE_APP")
    val commandsString = isSparkConsoleApp match {
      case Some("true") =>
        println("triggering spark console app")
        val initialCommandsFile = sys.env("COMMANDS_FILE")
        val commandsFile = new java.io.File(initialCommandsFile)
        commandsFile.exists() match {
          case true =>
            println(s"commands file $commandsFile exists, loading...")
            val commandString = sbt.IO.read(commandsFile)
            commandString
          case false =>
            println(s"commands file $commandsFile doesn't exists, ignored")
            ""
        }

      case Some(x) =>
        println("not triggering spark console app for >" + x + "<")
        ""

      case None =>
        println("not triggering spark console app for None")
        ""
    }
    commandsString
  }
}
