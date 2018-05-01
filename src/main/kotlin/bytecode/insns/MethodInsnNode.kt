package bytecode.insns

class MethodInsnNode(opcode: Int, val owner: String?,
                     val name: String?, val desc: String?, itf: Boolean): InsnNode(opcode){
//    val type: InstanceMethodInvoke.Type
}