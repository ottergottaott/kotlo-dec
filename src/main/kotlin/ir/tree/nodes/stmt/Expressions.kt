package ir.tree.nodes.stmt

import ir.tree.nodes.Locals

interface Expression

data class AssignmentLocal(val lvalue: Locals.Local, val rvalue: Expression) : Expression

data class DoubleConstant(val value: Double) : Expression

data class FloatConstant(val value: Float) : Expression

data class IntConstant(val value: Int) : Expression

data class LocalAccess(val localRef: Locals.Local) : Expression

data class ObjConstant(val value: Any?) : Expression

data class NumberCompare(val left: Expression, val right: Expression) : Expression

data class Increment(val local: Locals.Local, val value: Int) : Expression
