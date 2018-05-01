package ir.tree.nodes.stmt

import ir.tree.nodes.Locals

data class AssignmentLocal(val lvalue: Locals.Local, val rvalue: Instruction) : Instruction

data class DoubleConstant(val value: Double) : Instruction

data class FloatConstant(val value: Float) : Instruction

data class IntConstant(val value: Int) : Instruction

data class LocalAccess(val localRef: Locals.Local) : Instruction

data class ObjConstant(val value: Any?) : Instruction