package Tree

import AST
import Assignment
import CodeBlock
import Compiler.Parser
import Component
import Expression
import Node
import Token.Token


// TODO: add nested conditionals
class Comparison(val exprLeft: Expression, val compOp: Component, val exprRight: Expression): Node() {
    companion object {
        private val allowedOp = listOf("==", "!=", ">=", "<=", ">", "<")

        fun getComparisonFromTokens(tokens: MutableList<Token>): Comparison {
            return when {
                tokens.size < 3 -> {
                    throw Exception("A comparison should consist at least of 3 elements!")
                }
                tokens.size >= 3 -> {
                    val comps = tokens.filterIndexed() { index, token -> token.toString() in allowedOp }
                    when (comps.size) {
                        0 -> {
                            throw Exception("Comparisons should contain at least one comparison operator from " +
                                    "the following: $allowedOp")
                        }
                        1 -> {
                            val compIndex = tokens.indexOf(comps.first())
                            Comparison(Expression.getExpressionFromTokens(
                                tokens.slice(0 until compIndex).toMutableList()),
                                Component(comps.first().toString(), null),
                                Expression.getExpressionFromTokens(
                                    tokens.slice(compIndex + 1 until tokens.size).toMutableList()))
                        }
                        else -> {
                            throw Exception("No support for multiple comparisons yet")     // Temp
                        }
                    }
                }
                else -> {
                    throw Exception("Couldn't generate Comparison: Something wrong with the number of tokens")
                }
            }
        }
    }

    override fun reduce(): MutableList<Component> {
        return (exprLeft.reduce() +
                mutableListOf(compOp) +
                exprRight.reduce()).toMutableList()
    }

//    override fun reduceString(): String {
//        return toString()
//    }

    override fun toString(): String {
        return "$exprLeft $compOp $exprRight"
    }

}

open class Conditional(val condName: Component, val comp: Comparison, val codeBlocks: MutableList<CodeBlock>): CodeBlock() {

    companion object {
        fun getConditionalFromTokens(condStr: String, comp: Comparison, tokens: MutableList<MutableList<Token>>): Conditional {
//            println("inside")
            val ast = Parser().parse(tokens)
            return Conditional(Component(condStr, null),
                comp,
                ast.codeBlocks)

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
        }
    }

    override fun toString(): String {
        return "while ( $comp ) { $codeBlocks }"
    }

//    // TODO: fix this horrible stuff
//    override fun reduce(): MutableList<Component> {
//        return (term.reduce() +
//                (mathOp?.let { mutableListOf(mathOp) } ?: mutableListOf()) +
//                (expr?.let { expr.reduce() } ?: mutableListOf())
//                ).toMutableList()
//    }

    override fun reduceString(): String {
        return "while $comp:\n\t" +
                "${codeBlocks.joinToString(separator = "\n\t") { it.reduceString() }}"
    }

    override fun reduce(): MutableList<Component> {
        TODO("Not yet implemented")
    }

}

//class If(comparison: Comparison, codeBlocks: MutableList<CodeBlock>): Conditional(comparison, codeBlocks) {
//    override fun reduce(): MutableList<Component> {
//        return mutableListOf()
//    }
//}
//
//// TODO: Implement more than one comparison
//class While(val comparison: Comparison, val codeBlocks: MutableList<CodeBlock>): CodeBlock() {
//
//    companion object {
//        fun getExpressionFromTokens(tokens: MutableList<MutableList<Token>>): While {
//            return While(Comparison.getConditionalFromTokens(tokens.first()), mutableListOf(If()))
//
////            return when {
////                tokens.size == 1 || tokens.size == 2 -> {
////                    Expression(Term.getTermFromTokens(tokens), null, null)
////                }
////                tokens.size >= 3 -> {
////                    val firstToken = tokens.elementAt(0).toString()
////                    val secondToken = tokens.elementAt(1).toString()
////                    if (allowedOp.contains(secondToken)) {
////                        Expression(Term.getTermFromTokens(
////                            tokens.slice(0..0).toMutableList()),
////                            Component(secondToken, null),
////                            getExpressionFromTokens(tokens.slice(2 until tokens.size).toMutableList())
////                        )
////                    } else if (firstToken == "(") {
////                        val lastIndex = tokens.withIndex()
////                            .last { pair -> pair.value.toString() == ")" }
////                            .index
////                        Expression(Term.getTermFromTokens(
////                            tokens.slice(0..lastIndex).toMutableList()),
////                            null,
////                            null
////                        )
////                    }
////                    else {
////                        Expression(Term.getTermFromTokens(tokens), null, null)
////                    }
////                }
////                else -> {
////                    throw Exception("Couldn't parse the expression, something wrong with the amount of tokens.")
////                }
////            }
//        }
//    }
//
//    override fun toString(): String {
//        return "while ( $comparison ) { $codeBlocks }"
//    }
//
////    // TODO: fix this horrible stuff
////    override fun reduce(): MutableList<Component> {
////        return (term.reduce() +
////                (mathOp?.let { mutableListOf(mathOp) } ?: mutableListOf()) +
////                (expr?.let { expr.reduce() } ?: mutableListOf())
////                ).toMutableList()
////    }
//
//    override fun reduceString(): String {
//        return "while $comparison: ${codeBlocks}"
//    }
//
//    override fun reduce(): MutableList<Component> {
//        TODO("Not yet implemented")
//    }
//
//}