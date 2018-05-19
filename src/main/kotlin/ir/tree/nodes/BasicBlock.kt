package ir.tree.nodes

import ir.tree.visitors.VisitorInterface


class BasicBlock(val nodes: List<IRNode>) : IRNode {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitBasicBlock(nodes)
    }
}