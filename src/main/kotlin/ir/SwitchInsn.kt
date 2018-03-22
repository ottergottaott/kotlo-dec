package ir

class SwitchInsn(op: Int, val targets: MutableMap<Int, Int>, var default:
Int) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)}"

}
