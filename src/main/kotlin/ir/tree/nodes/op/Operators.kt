package ir.tree.nodes.op

import ir.tree.visitors.VisitorInterface


class BinaryOp(val left: Expression, val op: OperatorType,
               val right: Expression) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitBinaryOp(left, op, right)
    }
}

class NegativeOp(val ins: Expression) : Expression {
    override fun <T> accept(visitor: VisitorInterface<T>): T {
        return visitor.visitNegativeOp(ins)
    }
}

