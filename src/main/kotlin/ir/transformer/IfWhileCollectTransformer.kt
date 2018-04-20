package ir.transformer

import ir.tree.nodes.*

class IfWhileCollectTransformer : Transformer {
    override fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {

        val transformedNodes: MutableList<TreeNode> = mutableListOf()
//        nodes.forEachIndexed { idx, node ->
        var skipUntil = 0
        for ((node, idx) in nodes.zip(0 until nodes.size)) {
            if (idx < skipUntil) {
                continue
            }

            if (node is ConditionNode) {
                // find end of if body
                val elseIdx = nodes.indexOfFirst { it -> it.label() == node.target }
//                if (elseIdx < 0) {
//                    transformedNodes.add(node)
//                    continue
//                }
                val body = nodes.subList(idx, elseIdx)

                // check if body contains external goto
                val gotoInsns = findExternalGotoInsns(body)
                // check if loop
                val containsLoop = findLoopGotoInsns(node, nodes)

                if (containsLoop) {
                    // only while has more than one or only internal goto
                    val (whileNode, finalIdx) = collectWhileNode(currentNode = node,
                            nodes = nodes, conditionIdx = idx, elseIdx = elseIdx,
                            externGotos = gotoInsns)
                    transformedNodes.add(whileNode.transform(this))

                    skipUntil = finalIdx
                } else {
                    val externGoto = if (gotoInsns.isEmpty()) {
                        null
                    } else {
                        gotoInsns[0]
                    }
                    val (ifNode, finalIdx) = collectIfNode(currentNode = node,
                            nodes = nodes, conditionIdx = idx, elseIdx = elseIdx,
                            externGoto = externGoto)

                    transformedNodes.add(ifNode.transform(this))
                    skipUntil = finalIdx
                }


            } else {
                transformedNodes.add(node.transform(this))
                println("DEBUG")
            }
        }

        return if (transformedNodes.isEmpty()) {
            SequenceNode(nodes)
        } else {
            SequenceNode(transformedNodes)
        }
    }

    private fun collectIfNode(currentNode: ConditionNode, nodes: List<TreeNode>,
                              conditionIdx: Int, elseIdx: Int,
                              externGoto: GotoNode?): Pair<TreeNode, Int> {
        var body = nodes.subList(conditionIdx + 1, elseIdx)

        var finalIdx = -1
        if (externGoto != null) {
            finalIdx = nodes.indexOfFirst { it ->
                it.label() == externGoto.target
            }
        }

        var elseBodyNode: TreeNode? = null
        if (finalIdx >= 0) {
            body = nodes.subList(conditionIdx + 1, elseIdx)
            elseBodyNode = SequenceNode(nodes.subList(elseIdx, finalIdx))
        } else {
            finalIdx = elseIdx
        }
        val bodyNode = SequenceNode(body)
        val ifNode: TreeNode = IfNode(currentNode, bodyNode, elseBodyNode)

        return Pair(ifNode, finalIdx)
    }

    private fun collectWhileNode(currentNode: ConditionNode,
                                 nodes: List<TreeNode>, conditionIdx: Int, elseIdx: Int,
                                 externGotos: List<GotoNode>): Pair<TreeNode, Int> {
        // todo collect breakpoints
        val body = nodes.subList(conditionIdx + 1, elseIdx)
        val whileNode = WhileNode(currentNode, SequenceNode(body))
        return Pair(whileNode, elseIdx)
    }

    private fun findExternalGotoInsns(nodes: List<TreeNode>): List<GotoNode> {
        val gotoInsns: MutableList<GotoNode> = mutableListOf()
        nodes.forEach { node ->
            if (node is GotoNode) {
                val internalTarget = nodes.indexOfFirst { it -> it.label() == node.target }
                if (internalTarget == -1) {
                    gotoInsns.add(node)
                }
            }
        }

        return gotoInsns
    }

    private fun findLoopGotoInsns(conditionNode: ConditionNode,
                                  nodes: List<TreeNode>): Boolean {
        nodes.forEach { node ->
            if (node is GotoNode && node.target == conditionNode.label()) {
                return true
            }
        }

        return false
    }
}