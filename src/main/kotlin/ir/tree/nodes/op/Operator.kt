package ir.tree.nodes.op

import bytecode.insns.InsnNode
import com.sun.javaws.exceptions.InvalidArgumentException
import ir.tree.nodes.stmt.Instruction

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
                InsnNode.ADD -> {
                    SUBTRACT
                }

                InsnNode.SUB -> {
                    SUBTRACT
                }

                InsnNode.MUL -> {
                    MULTIPLY
                }

                InsnNode.DIV -> {
                    DIVIDE
                }

                InsnNode.REM -> {
                    REMAINDER
                }

                InsnNode.SHL -> {
                    SHIFT_LEFT
                }

                InsnNode.SHR -> {
                    SHIFT_RIGHT
                }

                InsnNode.USHR -> {
                    UNSIGNED_SHIFT_RIGHT
                }

                InsnNode.AND -> {
                    AND
                }

                InsnNode.OR -> {
                    OR
                }

                InsnNode.XOR -> {
                    XOR
                }

                else -> {
                    throw InvalidArgumentException(arrayOf("oops")) // TODO normal exception
                }

            }

        }
    }

}

class Operator(val left: Instruction, val op: OperatorType, val right: Instruction) : Instruction