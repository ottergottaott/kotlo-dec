package ir.regions

import bytecode.insns.GotoInstruction
import bytecode.insns.Instruction
import bytecode.insns.JumpInstruction
import bytecode.insns.LabelInstruction
import org.objectweb.asm.Label

sealed class Region(insnList: List<Instruction>) {
    val label: Label

    init {
        if (insnList.isEmpty()) {
            throw IllegalArgumentException("insnList should be not empty")
        }
        val firstInsn = insnList.first() as? LabelInstruction
                ?: throw IllegalArgumentException("first instruction should be LabelInstruction")
        label = firstInsn.labelNode.label
    }
}

class RawRegion(val insnList: List<Instruction>) : Region(insnList)

class JumpRegion(var insnList: List<Instruction>) : Region(insnList) {
    val target: Label

    init {
        val jumpInstruction = insnList.last() as? JumpInstruction
                ?: throw IllegalArgumentException("last instruction of JumpRegion should be JumpInstruction")
        target = jumpInstruction.target.labelNode.label
    }
}

class GotoRegion(var insnList: List<Instruction>) : Region(insnList) {
    val target: Label

    init {
        val gotoInstruction = insnList.last() as? GotoInstruction
                ?: throw IllegalArgumentException("last instruction of GotoRegion should be JumpInstruction")
        target = gotoInstruction.label.labelNode.label
    }


}

class IfRegion(val condition: JumpRegion, val body: List<Region>, val elseBody: List<Region> = emptyList()) : Region(condition.insnList)

class WhileRegion(val condition: JumpRegion, val body: List<Region>) : Region(condition.insnList)