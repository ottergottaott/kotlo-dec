package ir.collectors

import ir.regions.*
import kotlin.math.max


fun collectIfWhileRegion(blocks: List<Region>): List<Region> {
    var transformedNodes: List<Region> = emptyList()
    var skipUntil = 0
    blocks.forEachIndexed { idx, node ->
        when {
            idx < skipUntil -> return@forEachIndexed
            node is JumpRegion -> {
                val containsLoop = containsLoopJump(node, blocks.subList(idx, blocks.size))
                val (collectedNode, finalIdx) = if (containsLoop) {
                    collectWhileRegion(currentCondition = node,
                            nodes = blocks, conditionIdx = idx)
                } else {
                    collectIfRegion(currentCondition = node,
                            nodes = blocks, conditionIdx = idx)

                }
                skipUntil = finalIdx
                transformedNodes += collectedNode.collectIfWhileRegion()
            }
            else -> transformedNodes += node
        }
    }

    return transformedNodes
}

private fun collectIfRegion(currentCondition: JumpRegion, nodes: List<Region>,
                            conditionIdx: Int): Pair<IfRegion, Int> {
    val res = nodes.indexOfFirst { it.label == currentCondition.target }
    val elseIdx = if (res == -1) nodes.size else res
    val body = nodes.subList(conditionIdx + 1, elseIdx)


    val gotoInsnIdx = findIndexOfLastExternalGoto(conditionIdx, elseIdx, nodes)

    val finalIdx = max(elseIdx, gotoInsnIdx)
    val elseBodyNode: List<Region> = nodes.subList(elseIdx, finalIdx)

    return Pair(IfRegion(currentCondition, body, elseBodyNode), finalIdx)
}


private fun findIndexOfLastExternalGoto(conditionIdx: Int, elseIdx: Int, nodes: List<Region>): Int {
    if (conditionIdx > elseIdx) {
        return elseIdx
    }

    val gotoInsns = findExternalGotoInstructions(nodes.subList(conditionIdx, elseIdx))
    return if (gotoInsns.isNotEmpty()) {
        val gotoTargetIdx = nodes.indexOfFirst { it ->
            it.label == gotoInsns.last().target
        }

        if (gotoTargetIdx == -1) {
            nodes.size
        } else {
            findIndexOfLastExternalGoto(elseIdx, gotoTargetIdx, nodes)
        }

    } else {
        elseIdx
    }
}


private fun containsLoopJump(conditionNode: JumpRegion,
                             nodes: List<Region>): Boolean = nodes.any {
    it is GotoRegion && it.target == conditionNode.label
}


private fun findExternalGotoInstructions(nodes: List<Region>): List<GotoRegion> = nodes.filter { node ->
    node is GotoRegion && nodes.all { it.label != node.target }
}.map { it as GotoRegion }

private fun findLoopGotoInstructions(conditionNode: JumpRegion,
                                     nodes: List<Region>): List<GotoRegion> = nodes.filter { node ->
    node is GotoRegion && node.target == conditionNode.label
}.map { it as GotoRegion }

private fun collectWhileRegion(currentCondition: JumpRegion,
                               nodes: List<Region>, conditionIdx: Int): Pair<WhileRegion, Int> {
    val elseIdx = nodes.indexOfFirst { it.label == currentCondition.target }
    val body = nodes.subList(conditionIdx + 1, elseIdx)
    val breaks = findExternalGotoInstructions(body)
    val continues = findLoopGotoInstructions(currentCondition, body)
    val whileNode = WhileRegion(currentCondition, body)
    return Pair(whileNode, elseIdx)
}


private fun Region.collectIfWhileRegion(): Region = when (this) {
    is IfRegion -> this.collectIfWhileRegion()
    is WhileRegion -> this.collectIfWhileRegion()
    else -> this
}

private fun IfRegion.collectIfWhileRegion(): IfRegion {

    val newBody = collectIfWhileRegion(this.body)
    val newElseBody = collectIfWhileRegion(this.elseBody)

    return IfRegion(this.condition, newBody, newElseBody)
}

private fun WhileRegion.collectIfWhileRegion(): WhileRegion {
    val newBody = collectIfWhileRegion(this.body)
    return WhileRegion(this.condition, newBody)
}
