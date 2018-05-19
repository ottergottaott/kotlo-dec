package low.collectors

import bytecode.insns.GotoInstruction
import bytecode.insns.Instruction
import bytecode.insns.JumpInstruction
import bytecode.insns.LabelInstruction
import low.regions.GotoRegion
import low.regions.JumpRegion
import low.regions.RawRegion
import low.regions.Region
import java.util.*


fun splitToBasicBlocksByLabel(insnList: List<Instruction>): List<Region> {
    var result: List<RawRegion> = emptyList()
    val instructionsStack: Deque<Instruction> = LinkedList()

    insnList.forEach {
        if (it is LabelInstruction && instructionsStack.isNotEmpty()) {
            result += RawRegion(instructionsStack.toList())
            instructionsStack.clear()
        }
        instructionsStack.addLast(it)
    }

    if (instructionsStack.isNotEmpty()) {
        result += RawRegion(instructionsStack.toList())
    }
    return result
}

fun collectJumps(blockList: List<Region>): List<Region> {
    var toReturn: List<Region> = emptyList()
    blockList.forEach {
        when {
            it is RawRegion && isJumpBlock(it) -> {
                toReturn += JumpRegion(it.insnList)
            }
            it is RawRegion && isGotoBlock(it) -> {
                toReturn += collectGoto(GotoRegion(it.insnList))
            }
            else -> {
                toReturn += it
            }
        }
    }
    return toReturn
}

// if goto contains jump ?
private fun collectGoto(region: GotoRegion): List<Region> {

    var insnList = region.insnList
    val idxOfLastJump = insnList.indexOfLast {
        it is JumpInstruction
    }

    return if (idxOfLastJump != -1) {
        var result = listOf(JumpRegion(insnList.subList(0, idxOfLastJump + 1)))
        val insnListWithLabel = listOf(insnList.first()) +
                insnList.subList(idxOfLastJump + 1, insnList.size)

        result + GotoRegion(insnListWithLabel)
    } else {
        listOf(
                region
        )
    }

}

private fun isJumpBlock(region: RawRegion): Boolean =
        region.insnList.isNotEmpty() && region.insnList.last() is JumpInstruction

private fun isGotoBlock(region: RawRegion): Boolean =
        region.insnList.isNotEmpty() && region.insnList.last() is GotoInstruction
