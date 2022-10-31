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


class Lexer(fileName: String) {
    val tokens: MutableList<Token> = mutableListOf()
    val tokensInRows: MutableList<MutableList<Token>> = mutableListOf(mutableListOf())
    val sourceCode: String = ""

    // TODO: Maps and filters instead of for loops
    fun tokenize(){
        tokensInRows.map { row ->
            tokens.addAll(row)
            tokens.add(EOL("\n"))
        }
    }
    fun tokenizeInRows() {
        // Defining all keywords, symbols etc
        val keywords = listOf("if", "elif", "else", "while", "print")
//        val mathOperators = listOf("+", "-", "*", "/", "**", "%", "//")           // Long form
        val mathOperators = listOf("+", "-", "*", "/")                              // Short form
//        val comparisonOperators = listOf("==", "!=", ">", "<", ">=", "<=")        // Long form
        val comparisonOperators = listOf("==", "!=", ">", "<")                      // Short form
        val logicOperators = listOf("&&", "||", "!")
        val specialCharacters = listOf("(", ")", "{", "}", "=")

//        val allTokenTemplates = keywords + mathOperators + comparisonOperators + logicOperators + specialCharacters

        val tokens = mutableListOf<MutableList<Token>>()
        val lines = sourceCode.lines()

        // Breaking down lines into separate words and tokenizing them
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
//                row.add(EOL("\n"))            // Adds EOL character, currently not in use since rows are used instead
                tokens.add(row)
            }
        }
    }

    fun printTokens() {

    }

    fun printTokensInRows() {

    }

    fun readFromFile() {

    }

    fun generateTokensFromSource() {

    }

    fun generateTokensInRowsFromSource() {

    }

    fun generateTokensFromFile() {

    }

    fun generateTokensInRowsFromFile() {

    }

}


class Parser {

}

class Compiler(val lexer: Lexer, val parser: Parser)
fun main() {
    val l = Lexer("source.dc")
    val tokens = l.tokenizeInRows(str)
    tokens.map { row ->
        row.map { token -> print("$token ") }
        println()
    }

    val tokens2 = l.tokenize(str)
    print(tokens2)

//    val t = Keyword("if")
//    println(t)
//
//    val t2 = CustomInt("1")
//    println(t2.getValue())
}