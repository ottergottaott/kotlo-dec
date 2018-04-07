package bytecode.adapters.insns

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.VarInsnNode

class StoreInsnNode : AbstractInsnNode {

    private val insn: AbstractInsnNode

    constructor(varInsn: VarInsnNode) : super(varInsn.opcode) {
        this.insn = varInsn
    }

    constructor(insn: InsnNode) : super(insn.opcode) {
        this.insn = insn
    }

    override fun accept(p0: MethodVisitor?) {
        when (insn) {
            is VarInsnNode -> p0?.visitVarInsn(insn.opcode, insn.`var`)
            is InsnNode -> p0?.visitInsn(insn.opcode)
        }
    }

    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
        return insn.clone(labels)
    }

    override fun getType(): Int {
        return insn.type
    }

    fun isToArray(): Boolean {
        val opcode = insn.opcode
        val toLocalVariable = opcode == ISTORE ||
                opcode == LSTORE ||
                opcode == FSTORE ||
                opcode == DSTORE ||
                opcode == ASTORE

        return !toLocalVariable
    }
}