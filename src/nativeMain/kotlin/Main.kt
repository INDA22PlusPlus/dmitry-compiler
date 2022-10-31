open class Token(val str: String) {
    open fun getStr(): String {
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
    val t = Keyword("if")
    println(t.getStr())

    val t2 = CustomInt("1")
    println(t2.getValue())
}