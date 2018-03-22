package ir

class VarIntInsn(op: Int, val local: Int, val value: Int) : Insn(op) {
    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $local $value"
}
