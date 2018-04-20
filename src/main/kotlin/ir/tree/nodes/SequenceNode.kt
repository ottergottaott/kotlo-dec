package ir.tree.nodes

import ir.transformer.Transformer
import org.objectweb.asm.Label

class SequenceNode(val nodes: List<TreeNode>) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitSequenceNode(nodes)
    }

    override fun label(): Label {
        return Label()
    }
}