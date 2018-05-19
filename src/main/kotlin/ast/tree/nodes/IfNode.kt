package ast.tree.nodes

import ast.visitors.VisitorInterface

class IfNode(val condition: IRNode, val body: IRNode,
             val elseBody: IRNode) : IRNode {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitIfNode(condition, body, elseBody)
    }
}