open class Token(private val str: String) {
    override fun toString(): String {
        return str
    }
}

class Keyword(str: String): Token(str)

class MathOperator(str: String): Token(str)

class ComparisonOperator(str: String): Token(str)

class LogicOperator(str: String): Token(str)

class SpecialCharacter(str: String): Token(str)

class Variable(str: String): Token(str)

class EOL(str: String): Token(str) {
    override fun toString(): String {
        return "\\n"
    }
}
class CustomInt(str: String): Token(str) {
    private val value: Int = str.toInt()

    fun getValue(): Int{
        return value
    }
}


class Lexer {
    // TODO: Maps and filters instead of for loops
    fun tokenize(str: String): MutableList<Token> {
        val tokens = mutableListOf<Token>()
        tokenizeInRows(str).map { row -> tokens.addAll(row) }
        return tokens
    }
    fun tokenizeInRows(str: String): MutableList<MutableList<Token>> {
        val keywords = listOf("if", "elif", "else", "while", "print")
//        val mathOperators = listOf("+", "-", "*", "/", "**", "%", "//")        // Long form
        val mathOperators = listOf("+", "-", "*", "/")                           // Short form
//        val comparisonOperators = listOf("==", "!=", ">", "<", ">=", "<=")    // Long form
        val comparisonOperators = listOf("==", "!=", ">", "<")                  // Short form
        val logicOperators = listOf("&&", "||", "!")
        val specialCharacters = listOf("(", ")", "{", "}", "=")

//        val allTokenTemplates = keywords + mathOperators + comparisonOperators + logicOperators + specialCharacters

        val lines = str.lines()
        val tokens = mutableListOf<MutableList<Token>>()
        for (line in lines) {
            val lineSplit = line.split("\\s+".toRegex())
            val row = mutableListOf<Token>()
            for (token in lineSplit) {
                when {
                    keywords.contains(token) -> row.add(Keyword(token))
                    mathOperators.contains(token) -> row.add(MathOperator(token))
                    comparisonOperators.contains(token) -> row.add(ComparisonOperator(token))
                    logicOperators.contains(token) -> row.add(LogicOperator(token))
                    specialCharacters.contains(token) -> row.add(SpecialCharacter(token))

                    Regex("[a-zA-Z_]+").matches(token) -> row.add(Variable(token))
                    Regex("0|[1-9][0-9]*").matches(token) -> row.add(CustomInt(token))
                }
            }
            if (row.size > 0) {
//                row.add(EOL("\n"))
                tokens.add(row)
            }
        }

        return tokens
    }

    fun printTokensInRows(tokensInRows: MutableList<MutableList<Token>>) {
        tokensInRows.map { row ->
            row.map { token -> print("$token ") }
            println()
        }
    }
}


class Parser {

}

class InputReader {
    // TODO: Implement
    fun getSourceFromFile(fileName: String): String {
        return ""
    }

    fun getSourceFromString(): String {
        return """
            s = 0 + 1 + 2
            b = 3
            c_ = 45

            if ( s > b ) {
                print ( s )
            } elif ( s > 4 ) {
                print ( 5 )
            } else {
                print ( 6 )
            }

            while ( s < b ) {
                if b > s {
                    s = 7
                }
                s = s + 8
            }
        """.trimIndent()
    }
}
class Compiler(val inputReader: InputReader, val lexer: Lexer, val parser: Parser)
fun main() {
    val compiler = Compiler(InputReader(), Lexer(), Parser())
    val input = compiler.inputReader.getSourceFromString()
    val tokensInRows = compiler.lexer.tokenizeInRows(input)

    compiler.lexer.printTokensInRows(tokensInRows)

//    val t = Keyword("if")
//    println(t)
//
//    val t2 = CustomInt("1")
//    println(t2.getValue())
}