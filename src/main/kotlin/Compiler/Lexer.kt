package Compiler

import Token.*

class Lexer {
    // Defining all keywords, symbols etc
    private val keywords = listOf("if", "elif", "else", "while", "print")
    //        val mathOperators = listOf("+", "-", "*", "/", "**", "%", "//")           // Long form
    private val mathOperators = listOf("+", "-", "*", "/")                              // Short form
    //        val comparisonOperators = listOf("==", "!=", ">", "<", ">=", "<=")        // Long form
    private val comparisonOperators = listOf("==", "!=", ">", "<")                      // Short form
    private val logicOperators = listOf("&&", "||", "!")
    private val specialCharacters = listOf("(", ")", "{", "}", "=")
//        val allTokenTemplates = keywords + mathOperators + comparisonOperators + logicOperators + specialCharacters

    // TODO: Maps and filters instead of for loops
    fun tokenize(str: String): MutableList<Token> {
        val tokens = mutableListOf<Token>()
        tokenizeInRows(str).map { row ->
            tokens.addAll(row)
//            tokens.add(EOL("\n"))
        }
        return tokens
    }
    fun tokenizeInRows(str: String): MutableList<MutableList<Token>> {
        val tokens = mutableListOf<MutableList<Token>>()
        val lines = str.lines()

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

        return tokens
    }

    fun printTokensInRows(tokensInRows: MutableList<MutableList<Token>>) {
        tokensInRows.map { row ->
            row.map { token -> print("$token ") }
            println()
        }
    }
}