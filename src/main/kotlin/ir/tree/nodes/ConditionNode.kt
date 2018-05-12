package ir.tree.nodes

import bytecode.insns.Instruction
import bytecode.insns.JumpInstruction
import bytecode.insns.LabelInstruction
import ir.visitors.Transformer
import org.objectweb.asm.Label

typealias OpcodeList = List<Instruction>

class ConditionNode(val insnList: MutableList<OpcodeList>) : TreeNode {
    // last instruction in condition node is always
    // jump instruction with target
    // TODO remove last last
    val target = (insnList.last().last() as JumpInstruction).target.labelNode.label

    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitConditionNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0][0] is LabelInstruction) {
            return (insnList[0][0] as LabelInstruction).labelNode.label
        }

        return Label()
    }
}