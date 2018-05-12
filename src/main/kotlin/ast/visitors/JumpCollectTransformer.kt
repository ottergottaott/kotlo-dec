package ast.visitors


import ast.tree.nodes.*
import bytecode.insns.GotoInstruction
import bytecode.insns.Instruction
import bytecode.insns.JumpInstruction
import bytecode.insns.LabelInstruction
import java.util.*

class JumpCollectTransformer : Transformer {
    override fun visitUndoneNode(insnList: List<Instruction>): TreeNode {
        // find indexes of jump or goto insns
        var jumpInsnsIndices: MutableList<Int> = mutableListOf(0, insnList.size - 1)
        insnList.forEachIndexed { idx, it ->
            when (it) {
                is JumpInstruction -> {
                    val labelIdx = insnList
                            .subList(0, idx)
                            .indexOfLast { it -> it is LabelInstruction }
                    if (labelIdx >= 0) {
                        jumpInsnsIndices.add(labelIdx)
                    }
                    jumpInsnsIndices.add(idx)
                }

                is GotoInstruction -> {
                    jumpInsnsIndices.add(idx)

                    // find goto label before goto
                    val gotoLabelIdx = insnList
                            .subList(0, idx)
                            .indexOfLast { it -> it is LabelInstruction }
                    if (gotoLabelIdx >= 0) {
                        if (gotoLabelIdx in jumpInsnsIndices) {
                            jumpInsnsIndices.add(idx - 1)
                        } else {
                            jumpInsnsIndices.add(gotoLabelIdx)
                        }
                    }

                    // find idx of target targetted by goto instruction
                    // to break body of conditional
                    val labelIdx = insnList.indexOfFirst { lbl ->
                        lbl is LabelInstruction && lbl.labelNode.label == it.label.labelNode.label
                    }
                    if (labelIdx >= 0) {
                        jumpInsnsIndices.add(labelIdx)
                    }
                }
            }

        }

        val result: LinkedList<TreeNode> = LinkedList()
        jumpInsnsIndices = jumpInsnsIndices.distinct().sorted().toMutableList()

        loop@ for ((prev, next) in jumpInsnsIndices.zipWithNext()) {

            // TODO Delete this awful trick
            var prevIdx = prev
            if (insnList[prev] is JumpInstruction || insnList[prev] is GotoInstruction) {
                prevIdx = prev + 1
            }

            val nextInsn = insnList[next]
            when (nextInsn) {
                is JumpInstruction -> {
                    val blockInsnList = insnList.subList(prevIdx, next + 1)
                    if (result.isNotEmpty()) {
                        val prevNode = result.peek()
                        if (prevNode is ConditionNode
                                && prevNode.target == nextInsn.target.labelNode.label) {
                            prevNode.insnList.add(blockInsnList)
                            continue@loop
                        }
                    }
                    result.push(ConditionNode(mutableListOf(blockInsnList)))

                }
                is GotoInstruction -> {
                    if (insnList[prevIdx] !is LabelInstruction) {
                        if (prevIdx != next) {
                            result.push(UndoneNode(insnList.subList(prevIdx, next)))
                        }
                        result.push(GotoNode(listOf(insnList[next])))

                    } else {
                        result.push(GotoNode(insnList.subList(prevIdx, next + 1)))
                    }
                }
                else -> {
                    if (prevIdx == next) {
                        continue@loop
                    }
                    result.push(UndoneNode(insnList.subList(prevIdx, next)))
                }
            }
        }

        return SequenceNode(result.reversed())
    }
}