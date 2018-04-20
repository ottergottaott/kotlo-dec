package ir.tree.nodes

import ir.transformer.Transformer
import org.objectweb.asm.Label

interface TreeNode {
    fun transform(transformer: Transformer): TreeNode

    /**
     * Label of first bytecode instruction
     * @return label of first bytecode instruction or dummy label
     */
    fun label(): Label
}