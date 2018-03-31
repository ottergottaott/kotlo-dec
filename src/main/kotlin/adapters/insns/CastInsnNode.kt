package adapters.insns

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AbstractInsnNode

class CastInsnNode(opcode: Int) : AbstractInsnNode(opcode) {
    val fromType: Int = when (opcode) {
        I2L, I2F, I2D,
        I2B, I2S, I2C -> Type.INT
        L2I, L2F, L2D -> Type.LONG
        F2I, F2L, F2D -> Type.FLOAT
        D2I, D2L, D2F -> Type.DOUBLE
        else -> -1
    }

    val toType: Int = when (opcode) {
        I2L, F2L, D2L -> Type.LONG
        L2I, F2I, D2I -> Type.INT
        I2D, L2D, F2D -> Type.DOUBLE
        I2F, L2F, D2F -> Type.FLOAT
        I2S -> Type.SHORT
        I2B -> Type.BOOLEAN
        I2C -> Type.CHAR
        else -> -1
    }


    override fun accept(cv: MethodVisitor?) {
        cv?.visitInsn(opcode)
    }

    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
        return CastInsnNode(opcode)
    }

    override fun getType(): Int {
        return INSN // TODO ???
    }

}