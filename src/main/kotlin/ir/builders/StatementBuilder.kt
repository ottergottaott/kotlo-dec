package ir.builders

import bytecode.adapters.insns.IntInsnNode
import ir.tree.nodes.Locals
import ir.tree.nodes.UndoneNode
import ir.tree.nodes.stmt.Instruction
import java.util.*

//fun buildStatement(node: UndoneNode, locals: Locals, stack: Deque<Instruction>) {
//
//    node.insnList.forEach {it ->
//        when {
//            it is IntInsnNode -> {
//                stack.push()
//                return
//            }
//
//
//        }
//    }
//}