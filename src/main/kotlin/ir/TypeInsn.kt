package ir


class TypeInsn(op: Int, val type: String) : Insn(op) {
    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} $type"
}
