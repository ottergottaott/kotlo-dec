package bytecode.insns

import org.objectweb.asm.Handle
import org.objectweb.asm.tree.LabelNode

data class CastInstruction(override val opcode: Int) : Instruction

data class DoubleInstruction(override val opcode: Int, val operand: Double) : Instruction

data class FloatInstruction(override val opcode: Int, val operand: Float) : Instruction

data class GotoInstruction(override val opcode: Int, val label: LabelInstruction) : Instruction

data class IntInstruction(override val opcode: Int, val operand: Int) : Instruction

data class InvokeDynamicInstruction(override val opcode: Int, val name: String?, val desc: String?,
                                    val bsm: Handle?, val bsmArgs: List<Any?>) : Instruction

data class JumpInstruction(override val opcode: Int, val target: LabelInstruction) : Instruction

data class LabelInstruction(val labelNode: LabelNode) : Instruction {
    override val opcode: Int = -1
}

data class LdcInstruction(override val opcode: Int, val operand: Any?) : Instruction

data class LoadInstruction(override val opcode: Int, val local: Int) : Instruction

data class LongInstruction(override val opcode: Int, val operand: Long) : Instruction

data class MethodInstruction(override val opcode: Int, val owner: String?,
                             val name: String?, val desc: String?, val itf: Boolean) : Instruction

data class OpInstruction(override val opcode: Int) : Instruction

data class StoreInstruction(override val opcode: Int, val local: Int) : Instruction

data class IncInstruction(override val opcode: Int, val local: Int, val increment: Int) : Instruction