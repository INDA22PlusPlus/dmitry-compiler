package Compiler

import AST
import Assignment
import Print
import Tree.Conditional

// TODO: Fix this
data class SemanticReturn(val bool: Boolean, val msg: String?)

class SemanticAnalyzer {
    fun analyze(ast: AST, varStack: HashSet<String?>): SemanticReturn {
//        val varStack: HashSet<String?> = hashSetOf()
//        println(ast)
        for (codeBlock in ast.codeBlocks) {
            when (codeBlock::class) {
                Assignment::class -> {
                    val components = codeBlock.reduce()
                    val varName = components.first().str
//                println("$components")

                    // TODO: implement row and column for msg, better returns?
                    components.slice(2 until components.size)
                        .filter { Regex("[a-zA-Z_]+").matches(it.toString())}
                        .map { id -> if (id.str !in varStack) { return SemanticReturn(false,
                            "Variable '${id.str}' referenced before assignment!" +
                                    "\n\t Row: _ Column: _ -> $codeBlock" +
                                    "\n\t${" ".repeat(20 + codeBlock.toString().indexOf(id.str ?: ""))} ^")
                        } }
                    varStack.add(varName)
                }
                // TODO: fix duplication code
                Print::class -> {
                    val components = codeBlock.reduce()
                    components.filter { Regex("[a-zA-Z_]+").matches(it.toString())}
                        .map { id -> if (id.str !in varStack) { return SemanticReturn(false,
                            "Variable '${id.str}' referenced before assignment!" +
                                    "\n\t Row: _ Column: _ -> $codeBlock" +
                                    "\n\t${" ".repeat(20 + codeBlock.toString().indexOf(" ${id.str} " ?: "") + 1)} ^")
                        } }
                }
                Conditional::class -> {
                    val semRet = SemanticAnalyzer().analyze(codeBlock.getAST(), varStack)
//                    println(semRet)
                    if (!semRet.bool) {
                        return semRet
                    }
                }
            }
        }

        return SemanticReturn(true, null)
    }
}