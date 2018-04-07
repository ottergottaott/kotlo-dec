package ir.transformer

import ir.tree.nodes.IfNode
import ir.tree.nodes.SequenceNode
import ir.tree.nodes.TreeNode
import ir.tree.nodes.UndoneNode
import org.objectweb.asm.tree.InsnList
import java.util.*

interface Transformer {
    fun visitIfNode(condition: TreeNode, body: TreeNode,
                    elseBody: TreeNode): TreeNode {
        val newCondition= condition.transform(this)
        val newElseBody = elseBody.transform(this)
        val newBody = body.transform(this)

        return IfNode(newCondition, newBody, newElseBody)
    }
    fun visitUndoneNode(insnList: InsnList): TreeNode {
        return UndoneNode(insnList)
    }
    fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {
        val newNodes: MutableList<TreeNode> = LinkedList()

        for (node in nodes) {
            newNodes.add(node.transform(this))
        }

        return SequenceNode(newNodes)
    }
}