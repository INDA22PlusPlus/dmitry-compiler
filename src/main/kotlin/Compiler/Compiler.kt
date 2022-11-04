package Compiler

import AST
import java.io.File

class Compiler(val inputReader: InputReader, val lexer: Lexer, val parser: Parser,
               val semanticAnalyzer: SemanticAnalyzer) {

    // TODO: fix code (simple)
    fun compileFromAstToString(ast: AST): String {
        val semReturn = semanticAnalyzer.analyze(ast, hashSetOf())
        if (semReturn.bool) {
            return ast.reduceString()
        }
        else {
            throw Exception("Couldn't compile: ${semReturn.msg}")
        }
    }

    fun compileFromSourceToString(source : String): String {
        return compileFromAstToString(getAstFromSource(source))
    }

    fun compileFromAstToFile(ast: AST, fileName: String) {
        File(fileName).writeText(compileFromAstToString(ast))
    }

    fun getAstFromSource(source: String): AST {
        val input = inputReader.getSourceFromFile(source)
        val tokensInRows = lexer.tokenizeInRows(input)

        return parser.parse(tokensInRows, 1)
    }
    fun compileFromSourceToFile(source: String, target: String = "compiled.py") {
        compileFromAstToFile(getAstFromSource(source), target)
    }

    companion object {
        fun getCompiler(): Compiler {
            return Compiler(InputReader(), Lexer(), Parser(), SemanticAnalyzer())
        }
    }
}