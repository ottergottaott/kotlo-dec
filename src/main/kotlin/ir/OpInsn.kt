package ir

class OpInsn(op: Int) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)}"

}
