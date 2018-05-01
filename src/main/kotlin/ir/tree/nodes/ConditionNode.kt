package ir.tree.nodes

import bytecode.insns.InsnNode
import bytecode.insns.JumpInsnNode
import bytecode.insns.LabelInsnNode
import ir.visitors.Transformer
import org.objectweb.asm.Label

class ConditionNode(val insnList: List<InsnNode>) : TreeNode {
    // last instruction in condition node is always
    // jump instruction with target
    val target = (insnList.last() as JumpInsnNode).target.labelNode.label

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitConditionNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelInsnNode) {
            return (insnList[0] as LabelInsnNode).labelNode.label
        }

        return Label()
    }
}