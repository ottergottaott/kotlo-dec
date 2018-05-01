package bytecode.insns

import org.objectweb.asm.Handle
import org.objectweb.asm.tree.LabelNode

data class CastInsnNode(override val opcode: Int) : InsnNode

data class DoubleInsnNode(override val opcode: Int, val operand: Double) : InsnNode

data class FloatInsnNode(override val opcode: Int, val operand: Float) : InsnNode

data class GotoInsnNode(override val opcode: Int, val label: LabelInsnNode) : InsnNode

data class IntInsnNode(override val opcode: Int, val operand: Int) : InsnNode

data class InvokeDynamicInsnNode(override val opcode: Int, val name: String?, val desc: String?,
                                 val bsm: Handle?, val bsmArgs: List<Any?>) : InsnNode

data class JumpInsnNode(override val opcode: Int, val target: LabelInsnNode) : InsnNode

class LabelInsnNode(val labelNode: LabelNode) : InsnNode {
    override val opcode: Int = -1
}

data class LdcInsnNode(override val opcode: Int, val operand: Any?) : InsnNode

data class LoadInsnNode(override val opcode: Int, val local: Int) : InsnNode

data class LongInsnNode(override val opcode: Int, val operand: Long) : InsnNode

data class MethodInsnNode(override val opcode: Int, val owner: String?,
                          val name: String?, val desc: String?, val itf: Boolean) : InsnNode

data class OpInsnNode(override val opcode: Int) : InsnNode

data class StoreInsnNode(override val opcode: Int, val local: Int) : InsnNode