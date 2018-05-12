package ir.tree

import bytecode.insns.Instruction
import ir.tree.nodes.SequenceNode
import ir.tree.nodes.TreeNode
import ir.tree.nodes.UndoneNode
import ir.visitors.Transformer


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