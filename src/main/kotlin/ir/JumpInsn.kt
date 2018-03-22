package ir

class JumpInsn(op: Int, var target: Int) : Insn(op) {
    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $target"
}
