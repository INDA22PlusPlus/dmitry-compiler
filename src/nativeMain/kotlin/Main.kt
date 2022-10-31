//class TokenType(val value: String)

enum class Token(val str: String) {
    // Keywords
    IF("if"),
    WHILE("while"),
    PRINT("print"),
    //LET("let")

    // Math Operators
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    EXPONENT("**"),
    MODULUS("%"),
    FLOOR_DIVIDE("//"),

    // Comparison Operators
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER(">"),
    LESS("<"),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),

    // Logic Operators
    AND("&&"),
    OR("||"),
    NOT("!"),

    // Special Characters
    L_PAREN("("),
    R_PAREN(")"),
    L_BRACKET("{"),
    R_BRACKET("}"),
}

class Lexer {

//    fun tokenize(str: String): MutableList<Token> {
//        val inp = str.split("\\s+".toRegex())
//        println(inp)
//
//        val arr = mutableListOf<Token>()
//
//        for (e in inp) {
//            if (e.matches("^(0|[1-9][0-9]*)\$".toRegex())) {
//                arr.add(Token.INT)
//            } else if (e.matches("\-".toRegex())) {
//
//            }
//        }
//
//        println(arr)
//
//        return arr
//    }

}

fun main() {
//    val l = Lexer()
//    l.tokenize("0 + 1 + 1")
    val t = Token.AND
    println(t)
    println(t.str + t.name + t.ordinal)
}