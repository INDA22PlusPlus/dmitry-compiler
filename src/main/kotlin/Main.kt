import Compiler.Compiler

fun main(args: Array<String>) {
    val compiler = Compiler.getCompiler()
    compiler.compileFromSourceToFile(args[0], args[1])
//    Runtime.getRuntime().exec("python fib.py")
    val os = System.getProperty("os.name").lowercase()
    when {
        os.contains("win") -> {
            ProcessBuilder("cmd", "/C", "python", "${args[1]}")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        }

        os.contains("mac") -> {
            ProcessBuilder("python3", args[1])
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        }
    }
}