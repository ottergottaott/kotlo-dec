package ir.tree

import bytecode.adapters.insns.InsnNode
import ir.transformer.Transformer
import ir.tree.nodes.SequenceNode
import ir.tree.nodes.TreeNode
import ir.tree.nodes.UndoneNode
import org.objectweb.asm.tree.InsnList



class IRTree {
    val root: TreeNode

    constructor(root: TreeNode) {
        this.root = root
    }

    constructor(insnList: List<InsnNode>) {
        root = SequenceNode(listOf(UndoneNode(insnList)))
    }

    fun transform(transformer: Transformer): IRTree {
        val transformedRoot = root.transform(transformer)
        return IRTree(transformedRoot)
    }
}