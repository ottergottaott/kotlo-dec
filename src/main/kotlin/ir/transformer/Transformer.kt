package ir.transformer

import ir.tree.nodes.*
import org.objectweb.asm.tree.AbstractInsnNode
import java.util.*

interface Transformer {
    fun visitIfNode(condition: TreeNode, body: TreeNode,
                    elseBody: TreeNode?): TreeNode {
        val newCondition = condition.transform(this)
        val newElseBody = elseBody?.transform(this)
        val newBody = body.transform(this)

        return IfNode(newCondition, newBody, newElseBody)
    }

    fun visitUndoneNode(insnList: List<AbstractInsnNode>): TreeNode {
        return UndoneNode(insnList)
    }

    fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {
        val newNodes: MutableList<TreeNode> = LinkedList()

        for (node in nodes) {
            newNodes.add(node.transform(this))
        }

        return SequenceNode(newNodes)
    }

    fun visitConditionNode(insnList: List<AbstractInsnNode>): TreeNode {
        return ConditionNode(insnList)
    }

    fun visitWhileNode(condition: TreeNode, body: TreeNode): TreeNode {
        val newCondition = condition.transform(this)
        val newBody = body.transform(this)

        return WhileNode(newCondition, newBody)
    }
}