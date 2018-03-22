package ir

class LongInsn(op: Int, val value: Long) : Insn(op) {
    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)}$value"
}
