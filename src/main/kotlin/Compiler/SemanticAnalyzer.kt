package Compiler

import AST
import Assignment
import Print

// TODO: Fix this
data class SemanticReturn(val bool: Boolean, val msg: String?)

class SemanticAnalyzer {
    fun analyze(ast: AST): SemanticReturn {
        val varStack = hashSetOf<String?>()

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
                                    "\n\t Row: _ Column: _ -> ${codeBlock.reduceString()}" +
                                    "\n\t${" ".repeat(20 + codeBlock.reduceString().indexOf(id.str ?: ""))} ^")
                        } }
                    varStack.add(varName)
                }
                // TODO: fix duplication code
                Print::class -> {
                    val components = codeBlock.reduce()
                    components.filter { Regex("[a-zA-Z_]+").matches(it.toString())}
                        .map { id -> if (id.str !in varStack) { return SemanticReturn(false,
                            "Variable '${id.str}' referenced before assignment!" +
                                    "\n\t Row: _ Column: _ -> ${codeBlock.reduceString()}" +
                                    "\n\t${" ".repeat(20 + codeBlock.reduceString().indexOf(id.str ?: ""))} ^")
                        } }
                }
            }
        }

        return SemanticReturn(true, null)
    }
}