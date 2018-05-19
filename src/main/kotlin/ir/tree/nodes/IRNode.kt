package ir.tree.nodes

import ir.tree.visitors.VisitorInterface

interface IRNode {
    fun <T> accept(visitor: VisitorInterface<T>): T
}