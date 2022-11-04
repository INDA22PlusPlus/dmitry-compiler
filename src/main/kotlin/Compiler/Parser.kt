package Compiler

import AST
import Assignment
import Component
import Print
import Token.Token
import Tree.Comparison
import Tree.Conditional

class Parser {
    fun parse(tokensInRows: MutableList<MutableList<Token>>, nested: Int): AST {
        val ast = AST(mutableListOf())
        val tokenIterator = tokensInRows.iterator()
        while (tokenIterator.hasNext()) {
            var row = tokenIterator.next()
//            println(row)
            when (row.elementAt(0).toString().lowercase()) {
                "if" -> addConditional("if", tokenIterator, row, ast, nested)
//                "elif" -> {}
//                "else" -> {}
                "while" -> addConditional("while", tokenIterator, row, ast, nested)
                "break" -> {}
                "print" -> addPrint(row, ast)
                "//" -> {}          // Comments
                else -> addElse(row, ast)
            }

        }
        return ast
    }

    private fun addElse(row: MutableList<Token>, ast: AST) {
        when {
            row.size == 1 -> {}         // Special code for start-end brackets structures
            (row.elementAt(1).toString() == "=" && row.size >= 3) -> {
                ast.addCodeBlock(Assignment(
                    Component(row.elementAt(0).toString(), null),
                    Expression.getExpressionFromTokens(
                        row.slice(2 until row.size).toMutableList()))
                )
            }
            else -> {}
        }
    }

    fun addConditional(condName: String, tokenIterator: MutableIterator<MutableList<Token>>,
                       rowOld: MutableList<Token>, ast: AST, nested: Int) {
        var row = rowOld
        // TODO: Fix this, conditionals should also be of type Bool (or 0 and 1 int), also way to long
        if (row.size >= 7) {
//                        println("${row.elementAt(1)} \t ${row.elementAt(row.size - 2)} \t ${row.last()} \t " +
//                                "${row.slice(2 until row.size - 2).toMutableList()}")
            if (row.elementAt(1).toString() == "(" &&
                row.elementAt(row.size - 2).toString() == ")" &&
                row.last().toString() == "{") {

                val comp = Comparison.getComparisonFromTokens(row.slice(2 until row.size - 2).toMutableList())
//                            print(comp)
                var (lBrackets, rBrackets) = 1 to 0
                val stack = mutableListOf<MutableList<Token>>()
                while (tokenIterator.hasNext()) {
                    row = tokenIterator.next()
                    row.map { token ->
                        when (token.toString()) {
                            "{" -> {
                                lBrackets += 1
                            }
                            "}" -> {
                                rBrackets += 1
                            }
                            else -> {}
                        }
                    }
//                    println("$row \t $lBrackets \t $rBrackets")
                    if (lBrackets == rBrackets) { break }
                    stack.add(row)
                }
                ast.addCodeBlock(Conditional.getConditionalFromTokens(condName, comp, stack, nested))
            }
        } else {
            throw Exception("Conditionals should contain at least 7 elements")      // temp
        }
    }

    fun addPrint(row: MutableList<Token>, ast: AST) {
        if (row.elementAt(1).toString() == "(" &&
            row.last().toString() == ")" &&
            row.size == 3) {
            ast.addCodeBlock(Print(null))
        } else if (row.elementAt(1).toString() == "(" &&
            row.last().toString() == ")" &&
            row.size >= 4) {

            ast.addCodeBlock(Print(
                Expression.getExpressionFromTokens(
                    row.slice(2 until row.size - 1).toMutableList()
                )
            ))
        }
    }

//    companion object {
//        // TODO: Fix this i should stop doing bad code
//        fun parseStatic(tokensInRows: MutableList<MutableList<Token>>): AST {
//            return parse(tokensInRows)
//        }
//    }
}