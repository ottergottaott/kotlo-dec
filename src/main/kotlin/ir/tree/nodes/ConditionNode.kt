package ir.tree.nodes

import ir.transformer.Transformer
import org.objectweb.asm.Label
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode

class ConditionNode(val insnList: List<AbstractInsnNode>) : TreeNode {
    // last instruction in condition node is always
    // jump instruction with target
    val target = (insnList.last() as JumpInsnNode).label.label

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitConditionNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelNode) {
            return (insnList[0] as LabelNode).label
        }

        return Label()
    }
}