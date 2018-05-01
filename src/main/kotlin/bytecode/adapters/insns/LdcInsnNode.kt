package bytecode.adapters.insns

class LdcInsnNode(opcode: Int, val operand: Any?): InsnNode(opcode)