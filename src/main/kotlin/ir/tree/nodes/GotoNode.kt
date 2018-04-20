package ir.tree.nodes

import bytecode.adapters.insns.GotoInsnNode
import ir.transformer.Transformer
import org.objectweb.asm.Label
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.LabelNode

class GotoNode(val insnList: List<AbstractInsnNode>) : TreeNode {

    val target = (insnList.last() as GotoInsnNode).label.label
    override fun transform(transformer: Transformer): TreeNode {
        // TODO("not implemented") //To change body of created functions use
        return GotoNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelNode) {
            return (insnList[0] as LabelNode).label
        }

        return Label()
    }
}