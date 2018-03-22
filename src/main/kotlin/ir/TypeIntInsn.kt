package ir

class TypeIntInsn(op: Int, val type: String, val value: Int) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $type $value"

}
