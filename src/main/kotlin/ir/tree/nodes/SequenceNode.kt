package ir.tree.nodes

import ir.transformer.TransformerInterface

class SequenceNode(val nodes: List<TreeNode>) : TreeNode {
    override fun transform(transformer: TransformerInterface): TreeNode {
        return transformer.visitSequenceNode(nodes)
    }
}