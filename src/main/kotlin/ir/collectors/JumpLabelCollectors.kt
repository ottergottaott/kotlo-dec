package ir.collectors

import bytecode.insns.GotoInstruction
import bytecode.insns.Instruction
import bytecode.insns.JumpInstruction
import bytecode.insns.LabelInstruction
import ir.blocks.GotoRegion
import ir.blocks.JumpRegion
import ir.blocks.RawRegion
import ir.blocks.Region
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

fun collectJumps(blockList: List<Region>): List<Region> = blockList.map {
    when {
        it is RawRegion && isJumpBlock(it) -> {
            JumpRegion(it.insnList)
        }
        it is RawRegion && isGotoBlock(it) -> {
            GotoRegion(it.insnList)
        }
        else -> {
            it
        }
    }
}

fun isJumpBlock(region: RawRegion): Boolean =
        region.insnList.isNotEmpty() && region.insnList.last() is JumpInstruction

fun isGotoBlock(region: RawRegion): Boolean =
        region.insnList.isNotEmpty() && region.insnList.last() is GotoInstruction
