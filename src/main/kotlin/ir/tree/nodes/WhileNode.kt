package ir.tree.nodes

import ir.tree.visitors.VisitorInterface

class WhileNode(val condition: IRNode,
                val body: IRNode) : IRNode {

    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitWhileNode(condition, body)
    }
}