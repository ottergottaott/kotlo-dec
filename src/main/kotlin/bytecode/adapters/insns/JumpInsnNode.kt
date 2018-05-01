package bytecode.adapters.insns

class JumpInsnNode(opcode: Int, val target: LabelInsnNode): InsnNode(opcode)