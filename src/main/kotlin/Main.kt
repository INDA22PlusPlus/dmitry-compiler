import Compiler.Compiler

fun main() {
    val compiler = Compiler.getCompiler()

    // First case
    compiler.compileFromSourceToFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\source1.dc",
    "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\compiled1.py")
}