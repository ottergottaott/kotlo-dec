package builders

import bytecode.insns.*
import ir.tree.nodes.Locals
import ir.tree.nodes.UndoneNode
import ir.tree.nodes.op.NegativeOperator
import ir.tree.nodes.op.Operator
import ir.tree.nodes.op.OperatorType
import ir.tree.nodes.stmt.*
import java.util.*

fun buildStatement(node: UndoneNode, locals: Locals, stack: Deque<Instruction>): Deque<Instruction> {

    node.insnList.forEach { it ->

        // OpNode
        when (it.opcode) {
            InsnNode.NOOP, InsnNode.LABEL -> {}

            InsnNode.POP -> {
                stack.pop()
            }

            InsnNode.NEG -> {
                val expr = stack.pop()
                stack.push(NegativeOperator(expr))
            }

            InsnNode.ADD, InsnNode.SUB, InsnNode.DIV,
            InsnNode.MUL, InsnNode.REM, InsnNode.SHR,
            InsnNode.SHL, InsnNode.USHR, InsnNode.AND,
            InsnNode.OR, InsnNode.XOR -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(Operator(left, OperatorType.fromOpcode(it.opcode), right))
            }

        }

        when(it) {
            is LoadInsnNode -> {
                val local = locals.findLocal(it.local)
                stack.push(LocalAccess(local))
            }

            is StoreInsnNode -> {
                val insn = stack.pop()
                val local = locals.findLocal(it.local)
                stack.push(AssignmentLocal(local, insn))
            }
            is LdcInsnNode -> {
                // TODO full class type
                val objToPush = it.operand
                stack.push(ObjConstant(objToPush))
            }

            is IntInsnNode -> {
                // TODO ??? all int ops are const
                val value = it.operand
                stack.push(IntConstant(value))
            }

            is DoubleInsnNode -> {
                val value = it.operand
                stack.push(DoubleConstant(value))
            }

            is FloatInsnNode -> {
                val value = it.operand
                stack.push(FloatConstant(value))
            }
        }

    }

    return stack
}