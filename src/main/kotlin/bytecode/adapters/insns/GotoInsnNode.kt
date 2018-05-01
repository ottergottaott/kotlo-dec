package bytecode.adapters.insns

class GotoInsnNode(opcode: Int, val label: LabelInsnNode) : InsnNode(opcode) {
//    override fun accept(cv: MethodVisitor?) {
//        cv?.visitJumpInsn(opcode, label.labelNode.label)
//    }
//
//    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
//        return GotoInsnNode(opcode, label)
//    }
//
//    override fun getType(): Int {
//        return JUMP_INSN
//    }
}