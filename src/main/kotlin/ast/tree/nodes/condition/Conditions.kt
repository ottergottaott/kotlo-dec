package ast.tree.nodes.condition

import ast.tree.nodes.stmt.Expression
import bytecode.insns.Instruction
import com.sun.javaws.exceptions.InvalidArgumentException


data class AndCodition(val args: List<Condition>) : Condition

data class OrCondition(val args: List<Condition>) : Condition

enum class CompareOperator(val representation: String) {
    EQUAL(" == "),
    NOT_EQUAL(" != "),
    LESS_EQUAL(" <= "),
    GREATER_EQUAL(" >= "),
    LESS(" < "),
    GREATER(" > ");

    companion object {
        fun fromOpcode(opcode: Int): CompareOperator {
            when(opcode) {
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
                    throw InvalidArgumentException(arrayOf("asdas")) // TODO NORMAL EXCEPTION
                }
            }
        }
    }
}

data class CompareCondition(val left: Expression, val cmp: CompareOperator,
                            val right: Expression): Condition


//data class BooleanCondition()
interface Condition