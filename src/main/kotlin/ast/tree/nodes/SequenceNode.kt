package ast.tree.nodes

import ast.visitors.Transformer
import org.objectweb.asm.Label

class SequenceNode(val nodes: List<TreeNode>) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitSequenceNode(nodes)
    }

    override fun label(): Label {
        return Label()
    }
}