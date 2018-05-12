package ast.tree.nodes

import ast.visitors.Transformer
import org.objectweb.asm.Label

class IfNode(val condition: TreeNode, val body: TreeNode,
             val elseIfs: List<TreeNode> = listOf(), val elseBody: TreeNode? = null) : TreeNode {

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitIfNode(condition, body, elseIfs, elseBody)
    }

    override fun label(): Label {
        return condition.label()
    }
}