package ir.tree.nodes

import bytecode.adapters.insns.InsnNode
import bytecode.adapters.insns.LabelInsnNode
import ir.transformer.Transformer
import org.objectweb.asm.Label
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.LabelNode

class UndoneNode(val insnList: List<InsnNode>) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitUndoneNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelInsnNode) {
            return (insnList[0] as LabelInsnNode).labelNode.label
        }

        return Label()
    }
}