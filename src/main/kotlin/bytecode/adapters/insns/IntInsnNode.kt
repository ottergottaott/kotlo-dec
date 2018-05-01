package bytecode.adapters.insns

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AbstractInsnNode

class IntInsnNode(opcode: Int, val operand: Int) : InsnNode(opcode) {

//    override fun accept(p0: MethodVisitor?) {
//        p0?.visitInsn(opcode)
//    }
//
//    override fun clone(labels: MutableMap<Any?, Any?>?): AbstractInsnNode {
//        return IntInsnNode(opcode)
//    }
//
//    override fun getType(): Int {
//        return when(opcode) {
//            ICONST_M1, ICONST_0, ICONST_1,
//            ICONST_2, ICONST_3, ICONST_4,
//            ICONST_5 -> Type.INT
//
//            LCONST_0, LCONST_1 -> Type.LONG
//            FCONST_0, FCONST_1, FCONST_2 -> Type.FLOAT
//            DCONST_0, DCONST_1 -> Type.DOUBLE
//            else -> -1
//        }
//    }
}