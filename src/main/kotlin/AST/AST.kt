// AST related classes

class Component(val str: String?, val value: Int?) {
    override fun toString(): String {
        return (str?.let { "$str" } ?: "") +
                (value?.let { "$value" } ?: "")
    }
}

abstract class Node() {
    abstract fun reduce(): MutableList<Component>

    open fun reduceString(): String {
        return toString()
    }
}

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