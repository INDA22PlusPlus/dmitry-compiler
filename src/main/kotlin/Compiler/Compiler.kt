package Compiler

import AST
import java.io.File

class Compiler(val inputReader: InputReader, val lexer: Lexer, val parser: Parser,
               val semanticAnalyzer: SemanticAnalyzer) {

    // TODO: fix code (simple)
    fun compileToString(ast: AST): String {
        val semReturn = semanticAnalyzer.analyze(ast)
        if (semReturn.bool) {
            return ast.reduceString()
        }
        else {
            throw Exception("Couldn't compile: ${semReturn.msg}")
        }
    }

    fun compileFromAstToFile(ast: AST, fileName: String = "compiled.py") {
        File(fileName).writeText(compileToString(ast))
    }

    fun compileFromSourceToFile(source: String, target: String = "compiled.py") {
        val input = inputReader.getSourceFromFile(source)
        val tokensInRows = lexer.tokenizeInRows(input)

        val ast = parser.parse(tokensInRows)

        compileFromAstToFile(ast, target)
    }

    companion object {
        fun getCompiler(): Compiler {
            return Compiler(InputReader(), Lexer(), Parser(), SemanticAnalyzer())
        }
    }
}