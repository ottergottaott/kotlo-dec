package bytecode.adapters.insns

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.LabelNode

class GotoInsnNode(opcode: Int, val label: LabelNode): AbstractInsnNode(opcode) {
    override fun accept(cv: MethodVisitor?) {
        cv?.visitJumpInsn(opcode, label.label)
    }

    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
        return GotoInsnNode(opcode, label)
    }

    override fun getType(): Int {
        return JUMP_INSN
    }
}