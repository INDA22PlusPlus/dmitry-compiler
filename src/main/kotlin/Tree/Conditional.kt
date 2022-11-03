package Tree

import CodeBlock
import Component
import Expression
import Factor
import Node
import Term
import Token.Token


// TODO: add nested conditionals
class Conditional(val exprLeft: Expression, val compOp: Component, val exprRight: Expression): Node() {
    companion object {
        private val allowedOp = listOf("==", "!=", ">=", "<=", ">", "<")

        fun getConditionalFromTokens(tokens: MutableList<Token>): Conditional {
            return when {
                tokens.size < 3 -> {
                    throw Exception("A conditional should consist at least of 3 elements!")
                }
                tokens.size >= 3 -> {
                    val conds = tokens.filterIndexed() { index, token -> token.toString() in allowedOp }
                    when (conds.size) {
                        0 -> {
                            throw Exception("Conditionals should contain at least one conditional operator from " +
                                    "the following: $allowedOp")
                        }
                        1 -> {
                            val condIndex = tokens.indexOf(conds.first())
                            Conditional(Expression.getExpressionFromTokens(
                                tokens.slice(0 until condIndex).toMutableList()),
                                Component(conds.first().toString(), null),
                                Expression.getExpressionFromTokens(
                                    tokens.slice(condIndex + 1 until tokens.size).toMutableList()))
                        }
                        else -> {
                            throw Exception("No support for multiple conditionals yet")     // Temp
                        }
                    }
                }
                else -> {
                    throw Exception("Something wrong with the number of tokens")
                }
            }
        }
    }

    override fun reduce(): MutableList<Component> {
        TODO("Not yet implemented")
    }

//    override fun reduceString(): String {
//        return toString()
//    }

    override fun toString(): String {
        return "$exprLeft $compOp $exprRight"
    }

}

class If(): CodeBlock() {
    override fun reduce(): MutableList<Component> {
        return mutableListOf()
    }
}

// TODO: Implement more than one comparison
class While(val conditional: Conditional, val codeBlocks: MutableList<CodeBlock>): CodeBlock() {

//    companion object {
//        private val allowedOp = listOf("+", "-")
//
//        fun getExpressionFromTokens(tokens: MutableList<MutableList<Token>>): Expression {
//            return when {
//                tokens.size == 1 || tokens.size == 2 -> {
//                    Expression(Term.getTermFromTokens(tokens), null, null)
//                }
//                tokens.size >= 3 -> {
//                    val firstToken = tokens.elementAt(0).toString()
//                    val secondToken = tokens.elementAt(1).toString()
//                    if (allowedOp.contains(secondToken)) {
//                        Expression(Term.getTermFromTokens(
//                            tokens.slice(0..0).toMutableList()),
//                            Component(secondToken, null),
//                            getExpressionFromTokens(tokens.slice(2 until tokens.size).toMutableList())
//                        )
//                    } else if (firstToken == "(") {
//                        val lastIndex = tokens.withIndex()
//                            .last { pair -> pair.value.toString() == ")" }
//                            .index
//                        Expression(Term.getTermFromTokens(
//                            tokens.slice(0..lastIndex).toMutableList()),
//                            null,
//                            null
//                        )
//                    }
//                    else {
//                        Expression(Term.getTermFromTokens(tokens), null, null)
//                    }
//                }
//                else -> {
//                    throw Exception("Couldn't parse the expression, something wrong with the amount of tokens.")
//                }
//            }
//        }
//    }

    override fun toString(): String {
        return "while ( $conditional ) { $codeBlocks }"
    }

//    // TODO: fix this horrible stuff
//    override fun reduce(): MutableList<Component> {
//        return (term.reduce() +
//                (mathOp?.let { mutableListOf(mathOp) } ?: mutableListOf()) +
//                (expr?.let { expr.reduce() } ?: mutableListOf())
//                ).toMutableList()
//    }

    override fun reduceString(): String {
        return "while $conditional: ${codeBlocks}"
    }

    override fun reduce(): MutableList<Component> {
        TODO("Not yet implemented")
    }

}