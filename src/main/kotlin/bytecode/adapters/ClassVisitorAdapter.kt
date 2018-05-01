package bytecode.adapters

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import java.util.*


class ClassVisitorAdapter : ClassVisitor(Opcodes.ASM5), Opcodes {
    var methodNodes: MutableList<MethodVisitorAdapter> = LinkedList()

    override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {

        val mv = MethodVisitorAdapter(access, name, desc, signature, exceptions)

        methodNodes.add(mv)
        return mv
    }
}