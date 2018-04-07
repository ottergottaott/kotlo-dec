package ir.tree.nodes

import ir.transformer.Transformer

class IfNode(val condition: TreeNode, val body: TreeNode,
             val elseBody: TreeNode): TreeNode {

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitIfNode(condition, body, elseBody)
    }
}