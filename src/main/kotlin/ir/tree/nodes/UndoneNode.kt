package ir.tree.nodes

import ir.transformer.Transformer
import org.objectweb.asm.tree.InsnList

class UndoneNode(val insnList: InsnList) : TreeNode {
    override fun transform(transformer: Transformer): TreeNode {
        return transformer.visitUndoneNode(insnList)
    }
}