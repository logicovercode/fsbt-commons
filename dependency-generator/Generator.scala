package com.logicovercode.fsbt.commons

import better.files.Dsl._
import org.scalatest.flatspec.AsyncFlatSpecLike
import org.scalatest.matchers.should.Matchers

import scala.meta._

class Generator extends AsyncFlatSpecLike with Matchers {

  def traitBody(allMethods: List[Stat]): String = {
    val fullDef = q"""
     package com.logicovercode.fsbt.commons
     {
        package dependencies{
        import FSbtConvertor._
        import DependencyDefaultVersionLogic._
          trait GeneratedModuleIds{
            ..${allMethods}
          }
        }
     }
     """
    fullDef.syntax
  }

  case class ModuleIdMethodDetails(fSbtModuleId: FSbtModuleId, methodName : String)

  type EitherModuleIDMethod = Either[String, ModuleIdMethodDetails]

  def parseModuleIdsConf(): List[EitherModuleIDMethod] = {
    val configFile = cwd / "dependency-generator-config" / "dependencies.conf"

    println(s"config files exists : ${configFile.exists}")

    for {
      line <- configFile.lines.toList
      isNonEmpty = !line.trim.isEmpty
      isNotStartingWithHash = !line.trim.startsWith("#")
      if (isNonEmpty && isNotStartingWithHash)
    } yield parse(line)
  }

  def parse(line: String): Either[String, ModuleIdMethodDetails] = {
    val crossPath = line.contains("%%")
    val str = line.replace("%%", "%").replace("\"", "").replace(":", "%")
    str.split("%") match {
      case Array(method_name, org, artifactId, version) =>

        val fSbtModuleId = FSbtModuleId(org.trim, artifactId.trim, Version(version.trim), crossPath)
        Right(ModuleIdMethodDetails(fSbtModuleId, method_name))
      case _                               => Left(s"$line is not parsable.")
    }
  }

  def twoDefs(moduleIdMethodDetails: ModuleIdMethodDetails): (Defn.Def, Defn.Def) = {

    import moduleIdMethodDetails._

    import fSbtModuleId._

    val v = version.version

    val statement: Stat = if (crossPath) {
      q"""$organization %% $artifactId % $v"""
    } else {
      q"""$organization % $artifactId % $v"""
    }

    val name = methodName.trim

    println("name is >" + name + "<")

    val defDefn1: Defn.Def = {
      val msg = s"default version not allowed for $name <allowed version $v>"
      val statements = List(
        q"""assert(isDefaultVersionEnabled(), $msg)""",
        statement
      )

      q"""
     def ${Term.Name(name)}() : FSbtModuleId = {
        ..${statements}
     }
   """
    }

    val defDefn2: Defn.Def = {
      val msg = s"$name version should be $v"
      val statements = List(
        q"""assert( verifyModuleIdVersion(Version($v), version ), $msg )""",
        statement
      )

      q"""
     def ${Term.Name(name)}(version : String) : FSbtModuleId = {
        ..${statements}
     }
   """
    }



    (defDefn1, defDefn2)
  }

  "generating dependencies from config" should "pass" in {

    val generatedModuleIdsFile = cwd / "generated-dependencies" / "dependencies" / "GeneratedModuleIds.scala"
    if (!generatedModuleIdsFile.exists) {
      mkdirs(generatedModuleIdsFile.parent)
      touch(generatedModuleIdsFile)
    } else {
      rm(generatedModuleIdsFile)
      touch(generatedModuleIdsFile)
    }

    val eitherModuleIds = parseModuleIdsConf()

    val errorModuleIdMsgs = eitherModuleIds collect { case Left(msg) => msg }
    val correctModuleIdMethodDetails = eitherModuleIds collect { case Right(fSbtMid) => fSbtMid }

    assert(errorModuleIdMsgs.size == 0, errorModuleIdMsgs.mkString("\n"))

    val allDefs = correctModuleIdMethodDetails.map { moduleIdMethodDetail =>
      println(moduleIdMethodDetail)
      val defs = twoDefs(moduleIdMethodDetail)
      List(defs._1, defs._2)
    }

    generatedModuleIdsFile.append(traitBody(allDefs.flatten))
    generatedModuleIdsFile.exists should be(true)
  }
}
