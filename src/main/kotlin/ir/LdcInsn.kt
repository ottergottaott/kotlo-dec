package ir

class LdcInsn(op: Int, val constant: Any?) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $constant"
}
