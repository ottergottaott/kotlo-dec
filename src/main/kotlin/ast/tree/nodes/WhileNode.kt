package ast.tree.nodes

import ast.visitors.VisitorInterface

class WhileNode(val condition: IRNode,
                val body: IRNode) : IRNode {

    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitWhileNode(condition, body)
    }
}