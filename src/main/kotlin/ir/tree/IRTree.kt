package ir.tree

import ir.transformer.TransformerInterface
import ir.tree.nodes.TreeNode


class IRTree(val root: TreeNode) {
    fun transform(transformer: TransformerInterface): IRTree {
        val transformedRoot = root.transform(transformer)
        return IRTree(transformedRoot)
    }
}