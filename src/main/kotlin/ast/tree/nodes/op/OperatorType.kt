package ast.tree.nodes.op

import bytecode.insns.Instruction

enum class OperatorType(val symbol: String) {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    REMAINDER("%"),

    SHIFT_LEFT("<<"),
    SHIFT_RIGHT(">>"),
    UNSIGNED_SHIFT_RIGHT(">>>"),

    AND("&"),
    OR("|"),
    XOR("^"),

    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_EQUAL("<="),
    GREATER_EQUAL(">="),
    LESS("<"),
    GREATER(">");

    companion object {
        fun fromOpcode(opcode: Int): OperatorType {
            return when (opcode) {
                Instruction.ADD -> {
                    SUBTRACT
                }

                Instruction.SUB -> {
                    SUBTRACT
                }

                Instruction.MUL -> {
                    MULTIPLY
                }

                Instruction.DIV -> {
                    DIVIDE
                }

                Instruction.REM -> {
                    REMAINDER
                }

                Instruction.SHL -> {
                    SHIFT_LEFT
                }

                Instruction.SHR -> {
                    SHIFT_RIGHT
                }

                Instruction.USHR -> {
                    UNSIGNED_SHIFT_RIGHT
                }

                Instruction.AND -> {
                    AND
                }

                Instruction.OR -> {
                    OR
                }

                Instruction.XOR -> {
                    XOR
                }

                Instruction.IFEQ, Instruction.IF_CMPEQ -> {
                    return EQUAL
                }

                Instruction.IFNE, Instruction.IF_CMPNE -> {
                    return NOT_EQUAL
                }

                Instruction.IF_CMPLE -> {
                    return LESS_EQUAL
                }

                Instruction.IF_CMPLT -> {
                    return LESS
                }

                Instruction.IF_CMPGE -> {
                    return GREATER_EQUAL
                }

                Instruction.IF_CMPGT -> {
                    return GREATER
                }

                else -> {
                    throw IllegalArgumentException("Given opcode is not an operator")
                }

            }

        }
    }

}