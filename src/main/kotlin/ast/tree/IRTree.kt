package ast.tree

import ast.tree.nodes.SequenceNode
import ast.tree.nodes.TreeNode
import ast.tree.nodes.UndoneNode
import ast.visitors.Transformer
import bytecode.insns.Instruction


class IRTree {
    val root: TreeNode
    val name: String

    constructor(root: TreeNode, name: String) {
        this.root = root
        this.name = name
    }

    constructor(insnList: List<Instruction>, name: String) {
        root = SequenceNode(listOf(UndoneNode(insnList)))
        this.name = name
    }

    fun transform(transformer: Transformer): IRTree {
        val transformedRoot = root.transform(transformer)
        return IRTree(transformedRoot, name)
    }
}