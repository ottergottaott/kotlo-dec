package ast.tree.nodes

import org.objectweb.asm.tree.LocalVariableNode

enum class TypeSignature {
    Int, Double,
}

class Locals {
    val instances: MutableList<Local>

    constructor() {
        instances = ArrayList()
    }

    constructor(localVariables: List<LocalVariableNode>) {
        instances = ArrayList()
        localVariables.forEach{
                it -> instances.add(Local(it.name, it.desc))
        }
    }

    class Local(val name: String, val desc: String)

    fun findLocal(idx: Int): Local {
        return instances[idx]
    }
}