package ir.tree

import bytecode.insns.InsnNode
import ir.visitors.Transformer
import ir.tree.nodes.SequenceNode
import ir.tree.nodes.TreeNode
import ir.tree.nodes.UndoneNode


class IRTree {
    val root: TreeNode
    val name: String

    constructor(root: TreeNode, name: String) {
        this.root = root
        this.name = name
    }

    constructor(insnList: List<InsnNode>, name: String) {
        root = SequenceNode(listOf(UndoneNode(insnList)))
        this.name = name
    }

    fun transform(transformer: Transformer): IRTree {
        val transformedRoot = root.transform(transformer)
        return IRTree(transformedRoot, name)
    }
}