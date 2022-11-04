import Compiler.Compiler
import Tree.Comparison

fun main(args: Array<String>) {
//    println(args[0])
    val compiler = Compiler.getCompiler()
//    println(compiler.compileFromSourceToString(args[0]))
    compiler.compileFromSourceToFile(args[0], args[1])
//    Runtime.getRuntime().exec("python fib.py")
    val os = System.getProperty("os.name").lowercase()
    when {
        os.contains("win") -> {
            ProcessBuilder("cmd", "/C", "python ${args[1]}")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        }

        os.contains("mac") -> {
            ProcessBuilder("python3 ${args[1]}")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        }
    }


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

//    compiler.compileFromSourceToFile(args[0], args[1])
//    C:\Users\chiri\IdeaProjects\dmitryc-compiler\src\main\resources\fib.dc C:\Users\chiri\IdeaProjects\dmitryc-compiler\src\main\resources\fib.py
}