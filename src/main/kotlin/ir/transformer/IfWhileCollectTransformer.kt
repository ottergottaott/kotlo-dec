package ir.transformer

import ir.tree.nodes.*

class IfWhileCollectTransformer : Transformer {
    override fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {

        val transformedNodes: MutableList<TreeNode> = mutableListOf()
        var skipUntil = 0
        loop@for ((node, idx) in nodes.zip(0 until nodes.size)) {
            when {
                idx < skipUntil -> continue@loop
                node is ConditionNode -> {
                    val containsLoop = containsLoopGotoInsns(node, nodes)
                    if (containsLoop) {
                        val (whileNode, finalIdx) = collectWhileNode(currentNode = node,
                                nodes = nodes, conditionIdx = idx)
                        transformedNodes.add(whileNode.transform(this))

                        skipUntil = finalIdx
                    } else {
                        val (ifNode, finalIdx) = collectIfNode(currentNode = node,
                                nodes = nodes, conditionIdx = idx)

                        transformedNodes.add(ifNode.transform(this))
                        skipUntil = finalIdx
                    }
                }
                else -> transformedNodes.add(node.transform(this))
            }
        }

        return SequenceNode(transformedNodes)
    }

    private fun collectIfNode(currentNode: ConditionNode, nodes: List<TreeNode>,
                              conditionIdx: Int): Pair<TreeNode, Int> {
        var elseIdx = nodes.indexOfFirst { it -> it.label() == currentNode.target }
        var body = nodes.subList(conditionIdx + 1, elseIdx)

        var elseIfs: MutableList<IfNode> = mutableListOf()
        var nextElseIdx = elseIdx

        while (nodes[nextElseIdx] is ConditionNode) {
            val currentIfElseNode = nodes[nextElseIdx] as ConditionNode
            elseIdx = nodes
                    .subList(nextElseIdx, nodes.size)
                    .indexOfFirst { it -> it.label() == currentIfElseNode.target }
            val elseIfBody = nodes.subList(nextElseIdx + 1, elseIdx + nextElseIdx)
            val ifToAppend = IfNode(currentIfElseNode, SequenceNode(elseIfBody))
            elseIfs.add(ifToAppend)
            nextElseIdx += elseIdx

            // todo ???
        }

        // check if body contains external goto
        val gotoInsns = findExternalGotoInsns(nodes.subList(conditionIdx + 1, nextElseIdx))

        var finalIdx = -1
        if (gotoInsns.isNotEmpty()) {
            finalIdx = nodes.indexOfFirst { it ->
                it.label() == gotoInsns.last().target
            }
        }

        var elseBodyNode: TreeNode? = null
        if (finalIdx >= 0) {
//            body = nodes.subList(conditionIdx + 1, elseIdx)
            elseBodyNode = SequenceNode(nodes.subList(nextElseIdx, finalIdx))
        } else {
            finalIdx = elseIdx
        }
        val bodyNode = SequenceNode(body)
        val ifNode: TreeNode = IfNode(currentNode, bodyNode, elseIfs, elseBodyNode)

        return Pair(ifNode, finalIdx)
    }

    private fun collectWhileNode(currentNode: ConditionNode,
                                 nodes: List<TreeNode>, conditionIdx: Int): Pair<TreeNode, Int> {
        // todo collect breakpoints
        var elseIdx = nodes.indexOfFirst { it -> it.label() == currentNode.target }
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

    private fun containsLoopGotoInsns(conditionNode: ConditionNode,
                                      nodes: List<TreeNode>): Boolean {
        nodes.forEach { node ->
            if (node is GotoNode && node.target == conditionNode.label()) {
                return true
            }
        }

        return false
    }
}