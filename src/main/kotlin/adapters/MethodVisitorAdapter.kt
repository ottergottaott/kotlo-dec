package adapters

import adapters.insns.ArithmeticInsnNode
import adapters.insns.ConstantInsnNode
import adapters.insns.ReturnInsnNode
import adapters.insns.StoreInsnNode
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.TypePath
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.VarInsnNode

class MethodVisitorAdapter(access: Int, name: String?,
                           desc: String?, signature: String?,
                           exceptions: Array<out String>?) :
        MethodNode(ASM5, access, name, desc, signature, exceptions) {

    override fun visitMultiANewArrayInsn(desc: String?, dims: Int) {
        super.visitMultiANewArrayInsn(desc, dims)
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        when (opcode) {
            ISTORE, LSTORE, FSTORE,
            DSTORE, ASTORE -> instructions.add(StoreInsnNode(VarInsnNode(opcode, `var`)))
            else -> super.visitVarInsn(opcode, `var`)
        }
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        super.visitJumpInsn(opcode, label)
    }

    override fun visitLdcInsn(cst: Any?) {
        super.visitLdcInsn(cst)
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        super.visitIntInsn(opcode, operand)
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
        super.visitTypeInsn(opcode, type)
    }

    override fun visitAnnotationDefault(): AnnotationVisitor {
        return super.visitAnnotationDefault()
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitAnnotation(desc, visible)
    }

    override fun visitTypeAnnotation(typeRef: Int, typePath: TypePath?, desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible)
    }

    override fun visitInvokeDynamicInsn(name: String?, desc: String?, bsm: Handle?, vararg bsmArgs: Any?) {
        super.visitInvokeDynamicInsn(name, desc, bsm, *bsmArgs)
    }

    override fun visitLabel(label: Label?) {
        super.visitLabel(label)
    }

    override fun visitTryCatchAnnotation(typeRef: Int, typePath: TypePath?, desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitTryCatchAnnotation(typeRef, typePath, desc, visible)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        super.visitMethodInsn(opcode, owner, name, desc, itf)
    }

    override fun visitInsn(opcode: Int) {
        when (opcode) {
                ICONST_M1, ICONST_0, ICONST_1,
                ICONST_2, ICONST_3, ICONST_4,
                ICONST_5, LCONST_0, LCONST_1,
                FCONST_0, FCONST_1, FCONST_2,
                DCONST_0, DCONST_1 -> instructions.add(ConstantInsnNode(opcode))
                IADD,LADD,FADD,
                DADD,ISUB,LSUB,
                FSUB,DSUB,IMUL,
                LMUL,FMUL,DMUL,
                IDIV,LDIV,FDIV,
                DDIV,IREM,LREM,
                FREM,DREM,INEG,
                LNEG,FNEG,DNEG,
                ISHL,LSHL,ISHR,
                LSHR,IUSHR,LUSHR,
                IAND,LAND,IOR,
                LOR,IXOR,LXOR -> instructions.add(ArithmeticInsnNode(opcode))
                IRETURN,LRETURN,FRETURN,
                DRETURN,ARETURN,RETURN -> instructions.add(ReturnInsnNode(opcode))
                else -> super.visitInsn(opcode)
        }
    }

    override fun visitInsnAnnotation(typeRef: Int, typePath: TypePath?, desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitInsnAnnotation(typeRef, typePath, desc, visible)
    }

    override fun visitParameterAnnotation(parameter: Int, desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitParameterAnnotation(parameter, desc, visible)
    }

    override fun visitIincInsn(`var`: Int, increment: Int) {
        super.visitIincInsn(`var`, increment)
    }

    override fun visitLineNumber(line: Int, start: Label?) {
        super.visitLineNumber(line, start)
    }

    override fun visitLocalVariableAnnotation(typeRef: Int, typePath: TypePath?, start: Array<out Label>?, end: Array<out Label>?, index: IntArray?, desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible)
    }

    // Can be useful
    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }

    // Can be useful
    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        super.visitMaxs(maxStack, maxLocals)
    }

    // Can be useful
    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) {
        super.visitTryCatchBlock(start, end, handler, type)
    }

    // Can be useful
    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }
}
