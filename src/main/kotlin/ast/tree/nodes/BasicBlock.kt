package ast.tree.nodes

import ast.visitors.VisitorInterface


class BasicBlock(val nodes: List<IRNode>) : IRNode {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitBasicBlock(nodes)
    }
}