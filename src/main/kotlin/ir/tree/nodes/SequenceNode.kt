package ir.tree.nodes

import ir.transformer.Transformer

class SequenceNode(val nodes: List<TreeNode>) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitSequenceNode(nodes)
    }
}