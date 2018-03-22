package ir

import invoke.InstanceMethodInvoke

class InvokeInsn(op: Int, val type: InstanceMethodInvoke.Type?, val owner: String,
                 val name: String, val description: String) : Insn(op) {

    override fun toString(): String = "${InsnStringRepr.opcodeToString(opcode)} " +
            "$owner $name $description"

}