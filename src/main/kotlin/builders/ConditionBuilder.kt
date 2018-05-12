package builders

import bytecode.insns.Instruction
import ir.tree.nodes.ConditionNode
import ir.tree.nodes.Locals
import ir.tree.nodes.condition.CompareCondition
import ir.tree.nodes.condition.CompareOperator
import ir.tree.nodes.condition.Condition
import ir.tree.nodes.stmt.Expression
import java.util.*


fun makeCondition(node: ConditionNode, locals: Locals, stack: LinkedList<Expression>): MutableList<Condition> {
    val blocks = node.insnList
    val orConditionArgs = mutableListOf<Condition>()
    for (block in blocks) {
        buildStatement(block, locals, stack)
        val op = block.last().opcode
        when (op) {
            Instruction.IF_CMPGT, Instruction.IF_CMPGE,
            Instruction.IF_CMPLT, Instruction.IF_CMPLE,
            Instruction.IF_CMPEQ, Instruction.IF_CMPNE,
            Instruction.IFEQ, Instruction.IFNE -> {
                val right = stack.pop()
                val left = stack.pop()
                orConditionArgs.add(CompareCondition(left, CompareOperator.fromOpcode(op), right))

            }
        }
    }

    return orConditionArgs
}