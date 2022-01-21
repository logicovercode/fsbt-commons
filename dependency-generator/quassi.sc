import scala.meta._

def createDef(name : String): Defn.Def = {

  val doubleQuotes = "\""


  q"""
     def ${Term.Name(name)} : Unit = {
        println("hello world")
        import FSbtConvertor._
        "com.github.pathikrit" %% "better-files" % "3.9.1"
     }
   """
}

val d1 = createDef("sample")
val d2 = createDef("test")
val d3 = createDef("example")

val allMethods = List(d1, d2, d3)

val fullDef = q"""
   package com.logicovercode.fsbt.commons
   {
      package abc{
        trait GenDependencies{
          ..${allMethods}
        }
      }
   }
   """

println(fullDef.syntax)

