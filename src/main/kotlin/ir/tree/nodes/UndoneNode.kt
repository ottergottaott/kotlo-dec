package ir.tree.nodes

import bytecode.insns.InsnNode
import bytecode.insns.LabelInsnNode
import ir.visitors.Transformer
import org.objectweb.asm.Label

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