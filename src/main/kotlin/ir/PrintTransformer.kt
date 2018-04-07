package ir

import ir.transformer.TransformerInterface
import ir.tree.nodes.TreeNode
import org.objectweb.asm.tree.InsnList
import debug.printInsnList

class PrintTransformer: TransformerInterface {
    override fun visitIfNode(condition: TreeNode, body: TreeNode, elseBody: TreeNode): TreeNode {
        println("IF NODE")
        return super.visitIfNode(condition, body, elseBody)
    }

    override fun visitUndoneNode(insnList: InsnList): TreeNode {
        println("UNDONE NODE")
        printInsnList(insnList)
        return super.visitUndoneNode(insnList)
    }

    override fun visitSequenceNode(nodes: List<TreeNode>): TreeNode {
        println("SEQUENCE NODE")
        return super.visitSequenceNode(nodes)
    }
}