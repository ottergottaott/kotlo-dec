package ir.tree.nodes

import ir.transformer.TransformerInterface
import org.objectweb.asm.tree.InsnList

class UndoneNode(val insnList: InsnList) : TreeNode {
    override fun transform(transformer: TransformerInterface): TreeNode {
        return transformer.visitUndoneNode(insnList)
    }
}