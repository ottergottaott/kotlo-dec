package ir.tree.nodes

import bytecode.insns.GotoInsnNode
import bytecode.insns.InsnNode
import bytecode.insns.LabelInsnNode
import ir.visitors.Transformer
import org.objectweb.asm.Label

class GotoNode(val insnList: List<InsnNode>) : TreeNode {

    val target = (insnList.last() as GotoInsnNode).label.labelNode.label
    override fun transform(transformer: Transformer): TreeNode {
        // TODO("not implemented") //To change body of created functions use
        return GotoNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelInsnNode) {
            return (insnList[0] as LabelInsnNode).labelNode.label
        }

        return Label()
    }
}