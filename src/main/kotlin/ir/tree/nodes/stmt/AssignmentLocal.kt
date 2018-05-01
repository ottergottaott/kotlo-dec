package ir.tree.nodes.stmt

import ir.transformer.Transformer
import ir.tree.nodes.Locals
import ir.tree.nodes.TreeNode
import org.objectweb.asm.Label

class AssignmentLocal(val lvalue: Locals.Local, val rvalue: Instruction) : Instruction