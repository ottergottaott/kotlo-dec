package builders

import bytecode.insns.*
import ir.tree.nodes.Locals
import ir.tree.nodes.OpcodeList
import ir.tree.nodes.op.NegativeOperator
import ir.tree.nodes.op.Operator
import ir.tree.nodes.op.OperatorType
import ir.tree.nodes.stmt.*
import java.util.*

fun buildStatement(insnList: OpcodeList, locals: Locals, stack: LinkedList<Expression>): List<Expression> {

    insnList.forEach { it ->

        // OpNode
        when (it.opcode) {
            Instruction.NOOP, Instruction.LABEL -> {}

            Instruction.POP -> {
                stack.pop()
            }

            Instruction.NEG -> {
                val expr = stack.pop()
                stack.push(NegativeOperator(expr))
            }

            Instruction.CMP -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(NumberCompare(left, right))
            }

            Instruction.ADD, Instruction.SUB, Instruction.DIV,
            Instruction.MUL, Instruction.REM, Instruction.SHR,
            Instruction.SHL, Instruction.USHR, Instruction.AND,
            Instruction.OR, Instruction.XOR -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(Operator(left, OperatorType.fromOpcode(it.opcode), right))
            }

            Instruction.DUP -> {
                stack.push(stack.peek())
            }
            Instruction.DUP_X1 -> {
                val v1 = stack.pop()
                val v2 = stack.pop()
                stack.push(v1)
                stack.push(v2)
                stack.push(v1)
            }
            Instruction.DUP_X2 -> {
                val v1 = stack.pop()
                val v2 = stack.pop()
                val v3 = stack.pop()
                stack.push(v1)
                stack.push(v3)
                stack.push(v2)
                stack.push(v1)
            }
            Instruction.DUP2 -> {
                val v1 = stack.pop()
                val v2 = stack.peek()
                stack.push(v1)
                stack.push(v2)
                stack.push(v1)
            }
            Instruction.DUP2_X1 -> {
                val v1 = stack.pop()
                val v2 = stack.pop()
                val v3 = stack.pop()
                stack.push(v2)
                stack.push(v1)
                stack.push(v3)
                stack.push(v2)
                stack.push(v1)

            }
            Instruction.DUP2_X2 -> {
                val v1 = stack.pop()
                val v2 = stack.pop()
                val v3 = stack.pop()
                val v4 = stack.pop()
                stack.push(v2)
                stack.push(v1)
                stack.push(v4)
                stack.push(v3)
                stack.push(v2)
                stack.push(v1)
            }

        }

        when(it) {
            is LoadInstruction -> {
                val local = locals.findLocal(it.local)
                stack.push(LocalAccess(local))
            }

            is StoreInstruction -> {
                val insn = stack.pop()
                val local = locals.findLocal(it.local)
                stack.push(AssignmentLocal(local, insn))
            }
            is LdcInstruction -> {
                // TODO full class type
                val objToPush = it.operand
                stack.push(ObjConstant(objToPush))
            }

            is IntInstruction -> {
                // TODO ??? all int ops are const
                val value = it.operand
                stack.push(IntConstant(value))
            }

            is DoubleInstruction -> {
                val value = it.operand
                stack.push(DoubleConstant(value))
            }

            is FloatInstruction -> {
                val value = it.operand
                stack.push(FloatConstant(value))
            }

            is IncInstruction -> {
                val local = locals.findLocal(it.local)
                stack.push(Increment(local, it.increment))
            }
        }

    }

    return stack.reversed()
}