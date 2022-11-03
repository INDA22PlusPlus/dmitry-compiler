import java.io.File

// Token related classes
class Component(val str: String?, val value: Int?) {
    override fun toString(): String {
        return (str?.let { "$str" } ?: "") +
                (value?.let { "$value" } ?: "")
    }
}

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

// Compiler related classes
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
            tokens.add(EOL("\n"))
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
//                    println((nextRow.elementAt(1).toString()) +
//                            (nextRow.last().toString()) +
//                            (nextRow.size))
                    if (nextRow.elementAt(1).toString() == "(" &&
                        nextRow.last().toString() == ")" &&
                        nextRow.size >= 4) {

//                        println(2 until nextRow.size)
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

class InputReader {
    // TODO: Move files to resources
    fun getSourceFromFile(fileName: String): String {
        return File(fileName).readText()
    }

    fun getSourceFromStringHard(): String {
        return """
            s = 0 + 1 + 2
            b = 3
            c_ = 45

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
    }

    fun getSourceFromStringSimple(): String {
        return """
            s = 0 + 1
        """.trimIndent()
    }

    fun getSourceFromStringSimpleAllCases(): String {
        return """
            a = 0 + 1
            b = 2 - 3
            c = 4 * 5
            d = 6 / 7
            
            a = 0 + 1
            b = 2 - a
            c = b * 5
            d = 6 / c
        """.trimIndent()
    }

    fun getSourceFromStringSimpleWrong(): String {
        return """
            a = b + 1
        """.trimIndent()
    }

    fun getSourceFromStringHarderWrong(): String {
        return """
            a = 3 * 8
            b = a - 8
            c = b + a
            d = e + c
            e = 1 - 3
        """.trimIndent()
    }

    // Known bug with larger expressions
    fun getSourceFromStringManyCases(): String {
        return """
            var = 0 + 1
            vars = 40 - 3 + 45
            letters_ = 20 - 109 * 4
            letters_more = 20 * 109 - 6
            abc = ( 7201 + 1039 ) - 7
            xyz = ( 2402 + ( 1109 * 4382 ) ) - 8
            abc_xyz = 9 / ( 2402 + ( 1109 * 4382 ) ) - 8
            y = - 1034
        """.trimIndent()
    }
}

// TODO: Fix this
data class SemanticReturn(val bool: Boolean, val msg: String?)

class SemanticAnalyzer {
    fun analyze(ast: AST): SemanticReturn {
        val varStack = hashSetOf<String?>()

        for (codeBlock in ast.codeBlocks) {
            when (codeBlock::class) {
                Assignment::class -> {
                    val components = codeBlock.reduce()
                    val varName = components.first().str
//                println("$components")

                    // TODO: implement row and column for msg, better returns?
                    components.slice(2 until components.size)
                        .filter { Regex("[a-zA-Z_]+").matches(it.toString())}
                        .map { id -> if (id.str !in varStack) { return SemanticReturn(false,
                            "Variable '${id.str}' referenced before assignment!" +
                                    "\n\t Row: _ Column: _ -> ${codeBlock.reduceString()}" +
                                    "\n\t${" ".repeat(20 + codeBlock.reduceString().indexOf(id.str ?: ""))} ^")
                        } }
                    varStack.add(varName)
                }
                // TODO: fix duplication code
                Print::class -> {
                    val components = codeBlock.reduce()
                    components.filter { Regex("[a-zA-Z_]+").matches(it.toString())}
                        .map { id -> if (id.str !in varStack) { return SemanticReturn(false,
                            "Variable '${id.str}' referenced before assignment!" +
                                    "\n\t Row: _ Column: _ -> ${codeBlock.reduceString()}" +
                                    "\n\t${" ".repeat(20 + codeBlock.reduceString().indexOf(id.str ?: ""))} ^")
                        } }
                }
            }
        }

        return SemanticReturn(true, null)
    }
}

class Compiler(val inputReader: InputReader, val lexer: Lexer, val parser: Parser,
               val semanticAnalyzer: SemanticAnalyzer) {

    // TODO: fix code (simple)
    fun compileToString(ast: AST): String {
        val semReturn = semanticAnalyzer.analyze(ast)
        if (semReturn.bool) {
            return ast.reduceString()
        }
        else {
            throw Exception("Couldn't compile: ${semReturn.msg}")
        }
    }

    fun compileToFile(ast: AST, fileName: String) {
        File(fileName).writeText(compileToString(ast))
    }

}

// AST related classes

// AST
class AST(val codeBlocks: MutableList<CodeBlock>): Node() {
    fun addCodeBlock(codeBlock: CodeBlock) {
        codeBlocks.add(codeBlock)
    }

    override fun toString(): String {
        return "==== AST ====\nProgram\n\t$codeBlocks"
    }

    override fun reduce(): MutableList<Component> {
        TODO("Not yet implemented")
    }

    override fun reduceString(): String {
        return codeBlocks.joinToString(separator = "\n") { it.reduceString() }
    }
}

abstract class Node() {
    abstract fun reduce(): MutableList<Component>

    open fun reduceString(): String {
        return toString()
    }
}

// Expression related classes


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

// CodeBlock based classes
abstract class CodeBlock: Node() {

}

class Assignment(val varName: Component, val expr: Expression): CodeBlock() {
    override fun toString(): String {
        return "\n\t\t| $varName = $expr |"
    }

    override fun reduce(): MutableList<Component> {
        return (mutableListOf(varName) + mutableListOf(Component("=", null)) + expr.reduce()).toMutableList()
    }

    override fun reduceString(): String {
        return "$varName = $expr"
    }
}

class If(): CodeBlock() {
    override fun reduce(): MutableList<Component> {
        return mutableListOf()
    }
}

class While: CodeBlock() {
    override fun reduce(): MutableList<Component> {
        return mutableListOf()
    }

}

class Print(val expr: Expression): CodeBlock() {

//    companion object {
//        fun getPrintFromTokens(tokens: MutableList<Token>): Print {
//
//        }
//    }
    override fun reduce(): MutableList<Component> {
        return expr.reduce()
    }

    override fun reduceString(): String {
        return "print(${expr.reduceString()})"
    }
}

fun main() {
    val compiler = Compiler(InputReader(), Lexer(), Parser(), SemanticAnalyzer())

    // First case
    // TODO: Fix absolute path
    var input = compiler.inputReader.getSourceFromFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\source1.dc")
    var tokensInRows = compiler.lexer.tokenizeInRows(input)

    var ast = compiler.parser.parse(tokensInRows)

//    println(ast)
//
//    println(compiler.semanticAnalyzer.analyze(ast))
//
//    println(compiler.compileToString(ast))
    compiler.compileToFile(ast, "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\compiled1.py")

//    // Second case
//    input = compiler.inputReader.getSourceFromStringSimpleWrong()
//    tokensInRows = compiler.lexer.tokenizeInRows(input)
//
//    ast = compiler.parser.parse(tokensInRows)
//
//    println(ast)
//
//    println(compiler.semanticAnalyzer.analyze(ast).bool)
//
//    compiler.compile(ast)

//    // Third case
//    input = compiler.inputReader.getSourceFromFile("C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\source2.dc")
//    tokensInRows = compiler.lexer.tokenizeInRows(input)
//
//    ast = compiler.parser.parse(tokensInRows)
//
////    println(ast)
////
////    println(compiler.semanticAnalyzer.analyze(ast).bool)
//
////    println(compiler.compileToString(ast))
//    compiler.compileToFile(ast, "C:\\Users\\chiri\\IdeaProjects\\dmitryc-compiler\\src\\main\\resources\\compiled2.py")
}