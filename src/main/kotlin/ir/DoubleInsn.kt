package ir

class DoubleInsn(op: Int, val value: Double) : Insn(op) {
    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $value"
}
