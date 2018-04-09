package debug

import bytecode.adapters.insns.*
import org.objectweb.asm.tree.*
import org.objectweb.asm.util.Printer

fun printInsnList(insnList: InsnList) {
    for (instruction in insnList) {
        when (instruction) {
            is DupInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}")
            }

            is PopInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}")
            }

            is PushInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.operand}")
            }

            is JumpInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.label.label}")
            }
            is InsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}  " +
                        "${instruction.type}")
            }
            is LabelNode -> {
                println("${instruction.label}") // ${instruction.label
                // .offset}")
            }

            is LdcInsnNode -> {
                println("${instruction.cst}")
            }

            is VarInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.`var`} ${instruction.type}")
            }

            is MethodInsnNode -> {
                println("${instruction.name} ${instruction.owner}")
            }

            is ConstantInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}")
            }

            is LoadInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.getVariableNumber()}")
            }
            is StoreInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.getVariableNumber()}")
            }

            is ReturnInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}")
            }

            is ArithmeticInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}")
            }
        }
    }
}