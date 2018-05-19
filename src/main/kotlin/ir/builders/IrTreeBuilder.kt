package ir.builders

import ir.tree.nodes.*
import ir.tree.nodes.op.Expression
import low.regions.*
import java.util.*


fun buildIrFromRegions(regions: List<Region>, locals: Locals,
                       stack: Deque<Expression>): IRNode = BasicBlock(
        regions.map { it.buildNode(locals, stack) }
)

fun Region.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    return when (this) {
        is IfRegion -> this.buildNode(locals, stack)
        is WhileRegion -> this.buildNode(locals, stack)
        is RawRegion -> this.buildNode(locals, stack)
        is JumpRegion -> this.buildNode(locals, stack)
        is GotoRegion -> this.buildNode(locals, stack)
    }
}

fun IfRegion.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    val completedCondition = condition.buildNode(locals, stack)
    val completedBody = buildIrFromRegions(body, locals, stack)
    val completedElse = buildIrFromRegions(elseBody, locals, stack)

    return IfNode(completedCondition, completedBody, completedElse)

}

fun WhileRegion.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    val completedCondition = condition.buildNode(locals, stack)
    val completedBody = buildIrFromRegions(body, locals, stack)

    return WhileNode(completedCondition, completedBody)
}

fun RawRegion.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    val completedRegion = buildStatement(insnList, locals, stack)
    stack.clear()

    return BasicBlock(completedRegion)
}

fun JumpRegion.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    return makeCondition(this, locals, stack)
}

fun GotoRegion.buildNode(locals: Locals, stack: Deque<Expression>): IRNode {
    val completedRegion = buildStatement(insnList, locals, stack)
    stack.clear()

    return BasicBlock(completedRegion)
}
