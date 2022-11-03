package Token

// Token related classes

open class Token(private val str: String) {
    override fun toString(): String {
        return str
    }
}

class Keyword(val str: String): Token(str)

class MathOperator(val str: String): Token(str)

class ComparisonOperator(val str: String): Token(str)

class LogicOperator(val str: String): Token(str)

class SpecialCharacter(val str: String): Token(str)

class Variable(val str: String): Token(str)

class EOL(val str: String): Token(str) {
    override fun toString(): String {
        return "\\n"
    }
}

class CustomInt(val str: String): Token(str) {
    private val value: Int = str.toInt()

    fun getValue(): Int {
        return value
    }
}