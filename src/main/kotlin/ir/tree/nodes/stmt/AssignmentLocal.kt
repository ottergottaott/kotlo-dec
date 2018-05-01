package ir.tree.nodes.stmt

import ir.tree.nodes.Locals

class AssignmentLocal(val lvalue: Locals.Local, val rvalue: Instruction) : Instruction