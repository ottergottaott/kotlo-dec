package bytecode.insns

class JumpInsnNode(opcode: Int, val target: LabelInsnNode): InsnNode(opcode)