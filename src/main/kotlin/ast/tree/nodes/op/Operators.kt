package ast.tree.nodes.op

import ast.tree.nodes.stmt.Expression
import bytecode.insns.Instruction
import com.sun.javaws.exceptions.InvalidArgumentException

enum class OperatorType(val precedence: Int, val symbol: String) {

    ADD(11, "+"),
    SUBTRACT(11, "-"),
    MULTIPLY(12, "*"),
    DIVIDE(12, "/"),
    REMAINDER(12, "%"),

    SHIFT_LEFT(10, "<<"),
    SHIFT_RIGHT(10, ">>"),
    UNSIGNED_SHIFT_RIGHT(10, ">>>"),

    AND(7, "&"),
    OR(5, "|"),
    XOR(6, "^");

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

                else -> {
                    throw InvalidArgumentException(arrayOf("oops")) // TODO normal exception
                }

            }

        }
    }

}

data class Operator(val left: Expression, val op: OperatorType, val right: Expression) : Expression

data class NegativeOperator(val ins: Expression) : Expression