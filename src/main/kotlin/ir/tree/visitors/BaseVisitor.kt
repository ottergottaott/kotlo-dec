import ir.tree.nodes.IRNode
import ir.tree.nodes.Locals
import ir.tree.nodes.op.Expression
import ir.tree.nodes.op.OperatorType
import ir.tree.visitors.VisitorInterface

class BaseVisitor : VisitorInterface<String> {

    var prefix = 0
    val step = "    "
    //    val delim = "&&&"
    override fun visitIfNode(condition: IRNode, body: IRNode, elseBody: IRNode): String {
        prefix += 1
        val bodyRepr = body.accept(this)
        val elseBodyRepr = elseBody.accept(this)
        prefix -= 1

        return """|if (${condition.accept(this)}) {
            |$bodyRepr
            |}
            |else {
            |$elseBodyRepr
            |}""".replaceIndentByMargin(step.repeat(prefix), "|")
    }

    override fun visitBasicBlock(regions: List<IRNode>): String {
        return regions.joinToString(separator = "\n") {
            "${step.repeat(prefix)}${it.accept(this)}"
        }
    }

    override fun visitWhileNode(condition: IRNode, body: IRNode): String {
        return """while (${condition.accept(this)}) {
            |    ${body.accept(this)}
            |}""".trimMargin()
    }

    override fun visitAssignmentLocal(lvalue: Locals.Local, rvalue: Expression): String {
        return "${step.repeat(prefix)}${lvalue.name} = ${rvalue.accept(this)}"
    }

    override fun visitIntConstant(value: Int): String {
        return value.toString()
    }

    override fun visitLocalAccess(localRef: Locals.Local): String {
        return localRef.name
    }

    override fun visitDoubleConstant(value: Double): String {
        return value.toString()
    }

    override fun visitFloatConstant(value: Float): String {
        return value.toString()
    }

    override fun visitIncrement(local: Locals.Local, value: Int): String {
        return "${local.name} + $value"
    }

    override fun visitObjConstant(value: Any?): String {
        return value.toString()
    }

    override fun visitNumberCompare(left: Expression, right: Expression): String {
        return ""
    }

    override fun visitBinaryOp(left: Expression, op: OperatorType, right: Expression): String {
        return "${left.accept(this)} ${op.symbol} ${right.accept(this)}"
    }

    override fun visitNegativeOp(ins: Expression): String {
        return "!${ins.accept(this)}"
    }

    override fun visitReturn(op: Expression): String {
        return "return ${op.accept( this)}"
    }

    override fun visitLongConstant(value: Long): String {
        return value.toString()
    }
}