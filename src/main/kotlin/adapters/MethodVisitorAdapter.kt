package adapters
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.ASM5
import org.objectweb.asm.tree.*

class MethodVisitorAdapter(access: Int, name: String?,
                           desc: String?, signature: String?,
                           exceptions: Array<out String>?) :
        MethodNode(ASM5, access, name, desc, signature, exceptions) {

    override fun visitMultiANewArrayInsn(desc: String?, dims: Int) {
        super.visitMultiANewArrayInsn(desc, dims)
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        super.visitVarInsn(opcode, `var`)
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
        super.visitInsn(opcode)
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
