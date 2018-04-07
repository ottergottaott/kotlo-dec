package debug

import bytecode.adapters.insns.*
import org.objectweb.asm.tree.*
import org.objectweb.asm.util.Printer

fun printInsnList(insnList: InsnList) {
    for (instruction in insnList) {
        when (instruction) {
            is JumpInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${instruction.label}")
            }
            is InsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]}  " +
                        "${instruction.type}")
            }
            is LabelNode -> {
                println("${instruction.type} ${instruction.label}")
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
                println("${Printer.OPCODES[instruction.opcode]} ${Printer
                        .TYPES[instruction.type]}")
            }
            is StoreInsnNode -> {
                println("${Printer.OPCODES[instruction.opcode]} " +
                        "${Printer.TYPES[instruction.type]}")
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