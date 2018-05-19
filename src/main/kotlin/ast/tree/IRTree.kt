package ast.tree

import ast.tree.nodes.IRNode


class IRTree {
    val root: IRNode
    val name: String

    constructor(root: IRNode, name: String) {
        this.root = root
        this.name = name
    }

//    constructor(insnList: List<Instruction>, name: String) {
//        root = BasicBlock(listOf(UndoneNode(insnList)))
//        this.name = name
//    }
//
//    fun transform(transformer: BaseVisitor): IRTree {
//        val transformedRoot = root.transform(transformer)
//        return IRTree(transformedRoot, name)
//    }
}