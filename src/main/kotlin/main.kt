import bytecode.asm.adapters.ClassVisitorAdapter
import bytecode.insns.Instruction
import ir.builders.buildIrFromRegions
import ir.tree.nodes.Locals
import low.collectors.collectIfWhileRegion
import low.collectors.collectJumps
import low.collectors.splitToBasicBlocksByLabel
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.LocalVariableNode
import java.io.FileInputStream
import java.util.*

fun decompileMethod(name: String, insnList: List<Instruction>, locals: Locals) {

    println("Method $name")
    var res = splitToBasicBlocksByLabel(insnList)
    res = collectJumps(res)
    res = collectIfWhileRegion(res)
    val tree = buildIrFromRegions(res, locals, LinkedList())
    println(tree.accept(BaseVisitor()))
}

fun main(args: Array<String>) {
    val fileInputStream = FileInputStream("build/classes/kotlin/main/samples/ifs/SimpleIfKt.class")
    val classReader = ClassReader(fileInputStream)

    val cw = ClassVisitorAdapter()
    classReader.accept(cw, 0)

    for (met in cw.methodNodes) {
        val locals = met.localVariables.map { it as LocalVariableNode }
        val res = decompileMethod(met.name, met.myInsns, Locals(locals))
    }
}