import bytecode.asm.adapters.ClassVisitorAdapter
import builders.buildStatement
import bytecode.asm.adapters.MethodVisitorAdapter
import ir.visitors.IfWhileCollectTransformer
import ir.visitors.JumpCollectTransformer
import ir.tree.IRTree
import ir.tree.nodes.Locals
import ir.tree.nodes.SequenceNode
import ir.tree.nodes.UndoneNode
import ir.tree.nodes.WhileNode
import ir.tree.nodes.stmt.Instruction
import org.objectweb.asm.tree.LocalVariableNode
import org.objectweb.asm.ClassReader
import java.io.FileInputStream
import java.util.*

fun decompileMethod(method: IRTree, locals: List<LocalVariableNode>): Deque<Instruction> {

    println("End of method \n \n")

    println("Method: ${method.name}")
    val newMethodTree = method.transform(JumpCollectTransformer())
            .transform(IfWhileCollectTransformer())

    val blockToTest = (((newMethodTree.root as SequenceNode).nodes[0] as SequenceNode).nodes[1] as WhileNode)
    val res = buildStatement((blockToTest.body as SequenceNode).nodes[1] as UndoneNode, Locals(locals), LinkedList())

    return res
}

fun main(args: Array<String>) {
    val fileInputStream = FileInputStream("build/classes/kotlin/main/samples/whiles/WhileKt.class")
    val classReader = ClassReader(fileInputStream)

    val cw = ClassVisitorAdapter()
    classReader.accept(cw, 0)

    val curMethod = cw.methodNodes[2]
    val methodTree = IRTree(curMethod.myInsns, curMethod.name)

    val locals = curMethod.localVariables.map { it as LocalVariableNode }
    val res = decompileMethod(methodTree, locals)

    println("-------------------------")
}