import Compiler.Compiler
import Tree.Conditional

fun main() {
    val compiler = Compiler.getCompiler()

    val conditional = Conditional.getConditionalFromTokens(compiler.lexer.tokenize("a + 3 == 3 * b"))
    println(conditional)

    // First case
//    compiler.compileFromSourceToString("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\fib.dc")
//    compiler.compileFromSourceToFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\fib.dc",
//    "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\fib.py")
}