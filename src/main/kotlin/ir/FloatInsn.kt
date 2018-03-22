package ir

class FloatInsn(op: Int, val value: Float) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $value"

}
