package ir.transformer

import bytecode.adapters.insns.InsnNode
import ir.tree.nodes.*
import org.objectweb.asm.tree.AbstractInsnNode
import java.util.*

interface Transformer {
    fun visitIfNode(condition: TreeNode, body: TreeNode,
                    elseIfs: List<TreeNode>, elseBody: TreeNode?): TreeNode {
        val newCondition = condition.transform(this)
        val newBody = body.transform(this)
        val newElseIfs: List<TreeNode> = elseIfs.map { it -> it.transform(this) }
        val newElseBody = elseBody?.transform(this)

        return IfNode(newCondition, newBody, newElseIfs, newElseBody)
    }

    fun visitUndoneNode(insnList: List<InsnNode>): TreeNode {
        return UndoneNode(insnList)
    }

    fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {
        val newNodes: MutableList<TreeNode> = LinkedList()

        for (node in nodes) {
            newNodes.add(node.transform(this))
        }

        return SequenceNode(newNodes)
    }

    fun visitConditionNode(insnList: List<InsnNode>): TreeNode {
        return ConditionNode(insnList)
    }

    fun visitWhileNode(condition: TreeNode, body: TreeNode): TreeNode {
        val newCondition = condition.transform(this)
        val newBody = body.transform(this)

        return WhileNode(newCondition, newBody)
    }
}