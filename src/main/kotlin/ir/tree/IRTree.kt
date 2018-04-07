package ir.tree

import ir.transformer.Transformer
import ir.tree.nodes.TreeNode


class IRTree(val root: TreeNode) {
    fun transform(transformer: Transformer): IRTree {
        val transformedRoot = root.transform(transformer)
        return IRTree(transformedRoot)
    }
}