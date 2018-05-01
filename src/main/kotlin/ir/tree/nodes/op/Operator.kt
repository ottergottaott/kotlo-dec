package ir.tree.nodes.op

import ir.tree.nodes.stmt.Instruction

class Operator(val left: Instruction, val op: OperatorType, val right: Instruction) : Instruction