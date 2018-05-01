package ir.tree.nodes

import ir.visitors.Transformer
import org.objectweb.asm.Label

class WhileNode(val condition: TreeNode,
                val body: TreeNode) : TreeNode {

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitWhileNode(condition, body)
    }

    override fun label(): Label {
        return condition.label()
    }
}