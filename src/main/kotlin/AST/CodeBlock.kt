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