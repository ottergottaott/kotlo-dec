package bytecode.adapters.insns

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.IntInsnNode


class PushInsnNode(opcode: Int, val operand: Int): AbstractInsnNode(opcode) {


    override fun accept(cv: MethodVisitor?) {
        cv?.visitIntInsn(opcode, operand)
    }

    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
        return PushInsnNode(opcode, operand)
    }

    override fun getType(): Int {
        return INT_INSN
    }


}