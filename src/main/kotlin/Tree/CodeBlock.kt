import Component
import Node

// CodeBlock based classes
abstract class CodeBlock: Node() {
    open fun getAST(): AST {
        return AST(mutableListOf())
    }
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

class Print(val expr: Expression?): CodeBlock() {

    //    companion object {
//        fun getPrintFromTokens(tokens: MutableList<Token>): Print {
//
//        }
//    }
    override fun reduce(): MutableList<Component> {
        return expr?.let { expr.reduce() } ?: mutableListOf()
    }

    override fun reduceString(): String {
        return "print(${expr?.let { "$expr" } ?: ""})"
    }

    override fun toString(): String {
        return "print ( ${expr?.let { "$expr" } ?: ""} )"
    }
}