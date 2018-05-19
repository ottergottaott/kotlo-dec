package ast.tree.nodes.op

import ast.tree.nodes.stmt.Expression
import ast.visitors.VisitorInterface


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

