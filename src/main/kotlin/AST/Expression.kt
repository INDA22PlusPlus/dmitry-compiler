import Token.Token

// Expression related classes

// TODO: Fix duplication in other related classes
// Finally saw how wrong this approach is and how it's not gonna work for all cases, but need to move on and finish
// so that at least the fibonacci works
class Expression(val term: Term, val mathOp: Component?, val expr: Expression?): Node() {
    companion object {
        private val allowedOp = listOf("+", "-")

        fun getExpressionFromTokens(tokens: MutableList<Token>): Expression {
            return when {
                tokens.size == 1 || tokens.size == 2 -> {
                    Expression(Term.getTermFromTokens(tokens), null, null)
                }
                tokens.size >= 3 -> {
                    val firstToken = tokens.elementAt(0).toString()
                    val secondToken = tokens.elementAt(1).toString()
                    if (allowedOp.contains(secondToken)) {
                        Expression(Term.getTermFromTokens(
                            tokens.slice(0..0).toMutableList()),
                            Component(secondToken, null),
                            getExpressionFromTokens(tokens.slice(2 until tokens.size).toMutableList())
                        )
                    } else if (firstToken == "(") {
                        val lastIndex = tokens.withIndex()
                            .last { pair -> pair.value.toString() == ")" }
                            .index
                        Expression(Term.getTermFromTokens(
                            tokens.slice(0..lastIndex).toMutableList()),
                            null,
                            null
                        )
                    }
                    else {
                        Expression(Term.getTermFromTokens(tokens), null, null)
                    }
                }
                else -> {
                    throw Exception("Couldn't parse the expression, something wrong with the amount of tokens.")
                }
            }
        }
    }

    override fun toString(): String {
        return term.toString() + (mathOp?.let { " $mathOp " } ?: "") + (expr?.let { "$expr" } ?: "")
    }

    // TODO: fix this horrible stuff
    override fun reduce(): MutableList<Component> {
        return (term.reduce() +
                (mathOp?.let { mutableListOf(mathOp) } ?: mutableListOf()) +
                (expr?.let { expr.reduce() } ?: mutableListOf())
                ).toMutableList()
    }
}

// TODO: Fix duplication in other related classes
class Term(val term: Term?, val mathOp: Component?, val factor: Factor): Node() {

    companion object {
        private val allowedOp = listOf("*", "/")

        fun getTermFromTokens(tokens: MutableList<Token>): Term {
            return when {
                tokens.size == 1 || tokens.size == 2 -> {
                    Term(null, null, Factor.getFactorFromTokens(tokens))
                }
                tokens.size >= 3 -> {
                    val firstToken = tokens.elementAt(0).toString()
                    val secondToken = tokens.elementAt(1).toString()
                    if (allowedOp.contains(secondToken)) {
                        Term(getTermFromTokens(
                            tokens.slice(0..0).toMutableList()),
                            Component(secondToken, null),
                            Factor.getFactorFromTokens(tokens.slice(2 until tokens.size).toMutableList())
                        )
                    } else if (firstToken == "(") {
                        val lastIndex = tokens.withIndex()
                            .last { pair -> pair.value.toString() == ")" }
                            .index
                        Term(null,
                            null,
                            Factor.getFactorFromTokens(
                                tokens.slice(0..lastIndex).toMutableList())
                        )
                    }
                    else {
                        Term(null, null, Factor.getFactorFromTokens(tokens))
                    }
                }
                else -> {
                    throw Exception("Couldn't parse the term, something wrong with the amount of tokens.")
                }
            }
        }
    }
    override fun toString(): String {
        return (term?.let { "$term" } ?: "") + (mathOp?.let { " $mathOp " } ?: "") + factor.toString()
    }

    override fun reduce(): MutableList<Component> {
        return ((term?.let { term.reduce() } ?: mutableListOf()) +
                (mathOp?.let { mutableListOf(mathOp) } ?: mutableListOf()) +
                factor.reduce()
                ).toMutableList()
    }
}

// TODO: Fix duplication in other related classes
class Factor(val expr: Expression?, val factor: Factor?, val int: Component?, val varName: Component?): Node() {
    companion object {
        fun getFactorFromTokens(tokens: MutableList<Token>): Factor {
            val firstToken = tokens.first()
            return when {
                tokens.size == 1 -> {
                    when {
                        Regex("[a-zA-Z_]+").matches(firstToken.toString()) -> {
                            Factor(null, null, null, Component(firstToken.toString(), null))
                        }
                        Regex("0|[1-9][0-9]*").matches(firstToken.toString()) -> {
                            Factor(null, null, Component(null, firstToken.toString().toInt()), null)
                        }
                        else -> {
                            throw Exception("A Factor consisting of one token should be a Variable or CustomInt")
                        }
                    }
                }
                tokens.size == 2 -> {
                    if (firstToken.toString() == "-") {
                        Factor(
                            null,
                            getFactorFromTokens(tokens.slice(1..1).toMutableList()),
                            null,
                            null
                        )
                    } else {
                        throw Exception("A Factor consisting of two tokens should begin with a '-' and end with a Factor")
                    }
                }
                tokens.size >= 3 -> {
                    val lastToken = tokens.last()
                    if (firstToken.toString() == "(" && lastToken.toString() == ")") {
//                        println(tokens)
                        Factor(
                            Expression.getExpressionFromTokens(tokens.slice(1 until tokens.size).toMutableList()),
                            null,
                            null,
                            null,
                        )
                    } else {
//                        println(tokens.toString())
                        throw Exception("A Factor consisting of three or more tokens should have " +
                                "following structure: '(' Factor ')'")
                    }
                }
                else -> {
                    throw Exception("Couldn't parse the term, something wrong with the amount of tokens.")
                }
            }
        }
    }
    override fun toString(): String {
        return (expr?.let { "(${expr})" } ?: "") +
                (factor?.let { "-${factor}" } ?: "")  +
                (int?.let { "$int" } ?: "") +
                (varName?.let { "$varName" } ?: "")
    }

    override fun reduce(): MutableList<Component> {
        return ((expr?.let { expr.reduce() } ?: mutableListOf()) +
                (factor?.let { factor.reduce() } ?: mutableListOf()) +
                (int?.let { mutableListOf(int) } ?: mutableListOf()) +
                (varName?.let { mutableListOf(varName) } ?: mutableListOf())
                ).toMutableList()
    }
}
