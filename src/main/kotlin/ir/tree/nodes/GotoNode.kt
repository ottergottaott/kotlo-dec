package ir.tree.nodes

import bytecode.insns.GotoInstruction
import bytecode.insns.Instruction
import bytecode.insns.LabelInstruction
import ir.visitors.Transformer
import org.objectweb.asm.Label

class GotoNode(val insnList: List<Instruction>) : TreeNode {

    val target = (insnList.last() as GotoInstruction).label.labelNode.label
    override fun transform(transformer: Transformer): TreeNode {
        // TODO("not implemented") //To change body of created functions use
        return GotoNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelInstruction) {
            return (insnList[0] as LabelInstruction).labelNode.label
        }

        return Label()
    }
}