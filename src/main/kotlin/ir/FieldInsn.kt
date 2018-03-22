package ir

class FieldInsn(op: Int, val owner: String, val name: String, val description: String) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $owner $name $description"

}
