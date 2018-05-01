package ir.tree.nodes

import bytecode.adapters.insns.*
import ir.transformer.Transformer
import org.objectweb.asm.Label
import org.objectweb.asm.tree.AbstractInsnNode

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