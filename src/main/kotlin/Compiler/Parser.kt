package Compiler

import AST
import Assignment
import Component
import Print
import Token.Token

class Parser {
    fun parse(tokensInRows: MutableList<MutableList<Token>>): AST {
        val ast = AST(mutableListOf())
        val tokenIterator = tokensInRows.iterator()
        while (tokenIterator.hasNext()) {
            val nextRow = tokenIterator.next()
//            println(nextRow)
            when (nextRow.elementAt(0).toString()) {
                "if" -> {}
//                "elif" -> {}
//                "else" -> {}
                "while" -> {}
                "break" -> {}
                "print" -> {
                    if (nextRow.elementAt(1).toString() == "(" &&
                        nextRow.last().toString() == ")" &&
                        nextRow.size >= 4) {

                        ast.addCodeBlock(Print(
                            Expression.getExpressionFromTokens(
                                nextRow.slice(2 until nextRow.size - 1).toMutableList()
                            )
                        ))
                    }
                }
                else -> {
                    if (nextRow.elementAt(1).toString() == "=" && nextRow.size >= 3) {

                        ast.addCodeBlock(Assignment(
                            Component(nextRow.elementAt(0).toString(), null),
                            Expression.getExpressionFromTokens(
                                nextRow.slice(2 until nextRow.size).toMutableList()))
                        )
                    }
                }
            }

        }

        return ast
    }
}