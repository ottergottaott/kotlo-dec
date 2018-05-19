package ir.tree.nodes.op

import ir.tree.nodes.IRNode
import ir.tree.nodes.Locals
import ir.tree.visitors.VisitorInterface

//import ast.tree.nodes.IRNode

interface Expression : IRNode

class AssignmentLocal(val lvalue: Locals.Local, val rvalue: Expression) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitAssignmentLocal(lvalue, rvalue)
    }
}

class DoubleConstant(val value: Double) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitDoubleConstant(value)
    }
}

class FloatConstant(val value: Float) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitFloatConstant(value)
    }
}

class IntConstant(val value: Int) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitIntConstant(value)
    }
}

class LongConstant(val value: Long): Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitLongConstant(value)
    }
}

class LocalAccess(val localRef: Locals.Local) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitLocalAccess(localRef)
    }
}

class ObjConstant(val value: Any?) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitObjConstant(value)
    }
}

class NumberCompare(val left: Expression, val right: Expression) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitNumberCompare(left, right)
    }
}

class Increment(val local: Locals.Local, val value: Int) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitIncrement(local, value)
    }
}

class Return(val op: Expression) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitReturn(op)
    }
}