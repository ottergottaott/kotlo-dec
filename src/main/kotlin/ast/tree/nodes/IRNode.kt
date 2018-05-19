package ast.tree.nodes

import ast.visitors.VisitorInterface

interface IRNode {
    fun <T> accept(visitor: VisitorInterface<T>): T
}