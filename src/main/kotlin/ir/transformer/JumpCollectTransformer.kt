package ir.transformer

import bytecode.adapters.insns.GotoInsnNode
import ir.tree.nodes.*
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import java.util.*

class JumpCollectTransformer : Transformer {
    override fun visitUndoneNode(insnList: List<AbstractInsnNode>): TreeNode {
        // find indexes of jump or goto insns
        var jumpInsns: MutableList<Int> = mutableListOf(0, insnList.size - 1)
        insnList.forEachIndexed { idx, it ->
            if (it is JumpInsnNode) {
                val labelIdx = insnList
                        .subList(0, idx)
                        .indexOfLast { it -> it is LabelNode }
                if (labelIdx >= 0) {
                    jumpInsns.add(labelIdx)
                }
                jumpInsns.add(idx)
            }

            if (it is GotoInsnNode) {
                jumpInsns.add(idx)

                // find goto label before goto
                val gotoLabelIdx = insnList
                        .subList(0, idx)
                        .indexOfLast { it -> it is LabelNode }
                if (gotoLabelIdx >= 0) {
                    if (gotoLabelIdx in jumpInsns) {
                        jumpInsns.add(idx - 1)
                    } else {
                        jumpInsns.add(gotoLabelIdx)
                    }
                }

                // find idx of target targetted by goto instruction
                // to break body of conditional
                val labelIdx = insnList.indexOfFirst { lbl ->
                    lbl is LabelNode && lbl.label == it.label.label
                }
                if (labelIdx >= 0) {
                    jumpInsns.add(labelIdx)
                }
            }
        }

        val result: LinkedList<TreeNode> = LinkedList()
        jumpInsns = jumpInsns.distinct().sorted().toMutableList()

        for ((prev, next) in jumpInsns.zipWithNext()) {

            // TODO Delete this awful trick
            var prevIdx = prev
            if (insnList[prev] is JumpInsnNode || insnList[prev] is GotoInsnNode) {
                prevIdx = prev + 1
            }

            val nextInsn = insnList[next]
            when (nextInsn) {
                is JumpInsnNode -> {
                    var blockInsnList = insnList.subList(prevIdx, next + 1)
                    if (result.isNotEmpty()) {
                        val prevNode = result.peek()
                        if (prevNode is ConditionNode
                                && prevNode.target == nextInsn.label.label) {
                            result.pop()
                            blockInsnList = prevNode.insnList + blockInsnList
                        }
                    }
                    result.push(ConditionNode(blockInsnList))

                }
                is GotoInsnNode -> {
//                    val blockInsnList = insnList.subList(prevIdx, next)
//                    result.push(UndoneNode(blockInsnList))
                    if (insnList[prevIdx] !is LabelNode) {
                        result.push(UndoneNode(insnList.subList(prevIdx, next)))
                        result.push(GotoNode(listOf(insnList[next])))

                    } else {
                        result.push(GotoNode(insnList.subList(prevIdx, next + 1)))
                    }
                }
                else -> {
//                    var nextIdx = next
//                    if (prevIdx == next) {
//                        nextIdx += 1
//                    }
                    result.push(UndoneNode(insnList.subList(prevIdx, next)))
                }
            }
        }

        return SequenceNode(result.reversed())
    }
}