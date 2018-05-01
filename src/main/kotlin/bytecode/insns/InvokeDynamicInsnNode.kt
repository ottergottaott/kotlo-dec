package bytecode.insns

import org.objectweb.asm.Handle

class InvokeDynamicInsnNode(opcode: Int, val name: String?,val desc: String?,
                            val bsm: Handle?, vararg val bsmArgs: Any?) : InsnNode(opcode)