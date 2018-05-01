package bytecode.insns



class StoreInsnNode(opcode: Int, val local: Int) : InsnNode(opcode)
//{

//    private val insn: AbstractInsnNode
//
//    constructor(varInsn: VarInsnNode) : super(varInsn.opcode) {
//        this.insn = varInsn
//    }
//
//    constructor(insn: InsnNode) : super(insn.opcode) {
//        this.insn = insn
//    }
//
//    override fun accept(p0: MethodVisitor?) {
//        when (insn) {
//            is VarInsnNode -> p0?.visitVarInsn(insn.opcode, insn.`var`)
//            is InsnNode -> p0?.visitInsn(insn.opcode)
//        }
//    }
//
//    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
//        return insn.clone(labels)
//    }
//
//    override fun getType(): Int {
//        return insn.type
//    }
//
//    fun getVariableNumber(): Int {
//        return when (insn) {
//            is VarInsnNode -> insn.`var`
//            else -> -1
//        }
//    }
//
//    fun isToArray(): Boolean {
//        val opcode = insn.opcode
//        val toLocalVariable = opcode == ISTORE ||
//                opcode == LSTORE ||
//                opcode == FSTORE ||
//                opcode == DSTORE ||
//                opcode == ASTORE
//
//        return !toLocalVariable
//    }
//}