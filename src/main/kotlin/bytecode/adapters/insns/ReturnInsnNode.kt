package bytecode.adapters.insns

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.AbstractInsnNode

class ReturnInsnNode(opcode: Int) : AbstractInsnNode(opcode) {
    override fun accept(cv: MethodVisitor?) {
        cv?.visitInsn(opcode)
    }

    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
        return ReturnInsnNode(opcode)
    }

    override fun getType(): Int {
        return INSN
    }
}