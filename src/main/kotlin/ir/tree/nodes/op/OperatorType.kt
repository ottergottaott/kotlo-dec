package ir.tree.nodes.op

enum class OperatorType private constructor(val precedence: Int, val symbol: String) {

    ADD(11, "+"),
    SUBTRACT(11, "-"),
    MULTIPLY(12, "*"),
    DIVIDE(12, "/"),
    REMAINDER(12, "%"),

    SHIFT_LEFT(10, "<<"),
    SHIFT_RIGHT(10, ">>"),
    UNSIGNED_SHIFT_RIGHT(10, ">>>"),

    AND(7, "&"),
    OR(5, "|"),
    XOR(6, "^")

}