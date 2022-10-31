open class Token(private val str: String) {
    override fun toString(): String {
        return str
    }
}

class Keyword(str: String): Token(str)

class MathOperation(str: String): Token(str)

class ComparisonOperation(str: String): Token(str)

class LogicOperation(str: String): Token(str)

class SpecialCharacters(str: String): Token(str)

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
        println(str)

        val lines = str.lines()
//        var inp = str.split("\\s+".toRegex())
        println(lines)

        val arr = mutableListOf<Token>()

        // Keywords

        for (line in lines) {
            val lineSplit = line.split("\\s+".toRegex())
            for (token in lineSplit) {
                when {
                    // Keywords
                    "if" == token -> arr.add(Keyword("if"))
                    "else" == token -> arr.add(Keyword("else"))
                    "elif" == token -> arr.add(Keyword("elif"))
                    "when" == token -> arr.add(Keyword("when"))
                    "print" == token -> arr.add(Keyword("print"))

                    Regex("^(0|[1-9][0-9]*)\$").matches(token) -> arr.add(CustomInt(token))
                }
                print(token)
                if (token.matches("^(0|[1-9][0-9]*)\$".toRegex())) {
                    arr.add(CustomInt(token))
                }
            }
            println()
        }

        println(arr)

        return arr
    }

}

fun main() {
    val l = Lexer()
    l.tokenize("""
        s = 0 + 1 + 2
        b = 3
        
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
//    val t = Keyword("if")
//    println(t)
//
//    val t2 = CustomInt("1")
//    println(t2.getValue())
}