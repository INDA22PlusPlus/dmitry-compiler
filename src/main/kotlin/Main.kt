import Compiler.Compiler
import Tree.Comparison

fun main() {
    val compiler = Compiler.getCompiler()

//    val comparison = Comparison.getComparisonFromTokens(compiler.lexer.tokenize("a + 3 == 3 * b"))
//    println(comparison)

//    val tokens = compiler.lexer.tokenizeInRows("""
//        x = 0
//        while ( x > 10 ) {
//            print ( x )
//            x = x + 1
//        }
//    """.trimIndent())
//
//    val ast = compiler.parser.parse(tokens)
////    println(ast)
//    println(compiler.compileFromAstToString(ast))

    // First case
//    compiler.compileFromSourceToFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\fib.dc",
//    "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\fib.py")

    compiler.compileFromSourceToFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\source.dc",
        "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\source.py")
}