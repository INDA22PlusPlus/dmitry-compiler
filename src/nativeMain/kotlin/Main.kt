import platform.posix.log

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

class CustomInt(str: String): Token(str) {
    private val value: Int = str.toInt()

    fun getValue(): Int{
        return value
    }
}


class Lexer {
    // TODO: Maps and filters instead of for loops
    fun tokenize(str: String): MutableList<Token> {
//        println(str)

        val lines = str.lines()
//        var inp = str.split("\\s+".toRegex())
//        println(lines)

        val arr = mutableListOf<Token>()

        val keywords = listOf("if", "elif", "else", "while", "print")
//        val mathOperators = listOf("+", "-", "*", "/", "**", "%", "//")        // Long form
        val mathOperators = listOf("+", "-", "*", "/")                           // Short form
//        val comparisonOperators = listOf("==", "!=", ">", "<", ">=", "<=")    // Long form
        val comparisonOperators = listOf("==", "!=", ">", "<")                  // Short form
        val logicOperators = listOf("&&", "||", "!")
        val specialCharacters = listOf("(", ")", "{", "}", "=")

//        val allTokenTemplates = keywords + mathOperators + comparisonOperators + logicOperators + specialCharacters

        for (line in lines) {
            val lineSplit = line.split("\\s+".toRegex())
            for (token in lineSplit) {
                when {
                    keywords.contains(token) -> arr.add(Keyword(token))
                    mathOperators.contains(token) -> arr.add(MathOperator(token))
                    comparisonOperators.contains(token) -> arr.add(ComparisonOperator(token))
                    logicOperators.contains(token) -> arr.add(LogicOperator(token))
                    specialCharacters.contains(token) -> arr.add(SpecialCharacter(token))

                    Regex("[a-zA-Z_]+").matches(token) -> arr.add(Variable(token))
                    Regex("0|[1-9][0-9]*").matches(token) -> arr.add(CustomInt(token))
                }
//                print(token)
//                if (token.matches("^(0|[1-9][0-9]*)\$".toRegex())) {
//                    arr.add(CustomInt(token))
//                }
            }
//            println()
        }

//        println(arr)

        return arr
    }
}

fun main() {
    val l = Lexer()
    var tokens = l.tokenize("""
        s = 0 + 1 + 2
        b_ = 3
        
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
    )

    println(tokens)

//    val t = Keyword("if")
//    println(t)
//
//    val t2 = CustomInt("1")
//    println(t2.getValue())
}