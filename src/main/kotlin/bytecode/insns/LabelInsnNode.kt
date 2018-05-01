package bytecode.insns

import org.objectweb.asm.tree.LabelNode

// TODO rewrite or say that this is proxy
class LabelInsnNode(val labelNode: LabelNode) : InsnNode(-1)