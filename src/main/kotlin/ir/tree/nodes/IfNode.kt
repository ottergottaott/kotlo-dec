package ir.tree.nodes

import ir.transformer.TransformerInterface

class IfNode(val condition: TreeNode, val body: TreeNode,
             val elseBody: TreeNode): TreeNode {

    override fun transform(transformer: TransformerInterface): TreeNode {
        return transformer.visitIfNode(condition, body, elseBody)
    }
}