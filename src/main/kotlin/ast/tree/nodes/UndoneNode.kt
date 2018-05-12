package ast.tree.nodes

import ast.visitors.Transformer
import bytecode.insns.Instruction
import bytecode.insns.LabelInstruction
import org.objectweb.asm.Label

class UndoneNode(val insnList: List<Instruction>) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitUndoneNode(insnList)
    }

    override fun label(): Label {
        if (insnList.isNotEmpty() && insnList[0] is LabelInstruction) {
            return (insnList[0] as LabelInstruction).labelNode.label
        }

        return Label()
    }
}