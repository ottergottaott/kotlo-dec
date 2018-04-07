package ir.tree.nodes

import ir.transformer.Transformer

interface TreeNode {
    fun transform(transformer: Transformer): TreeNode
}