package ir.builders

import bytecode.insns.JumpInstruction
import ir.tree.nodes.Locals
import ir.tree.nodes.op.BinaryOp
import ir.tree.nodes.op.Expression
import ir.tree.nodes.op.OperatorType
import low.regions.JumpRegion
import java.util.*


fun makeCondition(node: JumpRegion, locals: Locals, stack: Deque<Expression>): Expression {
    val insnList = node.insnList

    // need to split by jumps
    val jumpIndexes = listOf(0) +
            insnList.mapIndexed { index, instruction ->
                if (instruction is JumpInstruction) {
                    index
                } else {
                    -1
                }
            }.filter { it > -1 }


    var res: Expression? = null

    for ((from, to) in jumpIndexes zip jumpIndexes.drop(1)) {
        buildStatement(insnList.subList(from, to), locals, stack)
        val right = stack.pop()
        val left = stack.pop()

        val newExpression = BinaryOp(left, OperatorType.fromOpcode(insnList[to].opcode), right)

        res = if (res != null) {
            BinaryOp(res, OperatorType.OR, newExpression)
        } else {
            newExpression
        }

    }

    if (res == null) {
        throw TODO("normal exception")
    } else {
        return res
    }

}