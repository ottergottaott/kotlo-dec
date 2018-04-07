package ir.tree.nodes

import ir.transformer.TransformerInterface

interface TreeNode {
    fun transform(transformer: TransformerInterface): TreeNode
}