package bytecode.insns

interface InsnNode {
    val opcode: Int

    companion object {
        const val LABEL = -1

        const val NOOP = 0 // OpInsnNode
        const val PUSH = 1 // -
        const val ICONST = 2 // IntInsnNode
        const val LCONST = 3 // LongInsnNode
        const val FCONST = 4 // FloatInsnNode
        const val DCONST = 5 // DoubleInsnNode

        const val LOCAL_LOAD = 10 // LoadInsnNode
        const val LOCAL_STORE = 11 // StoreInsnNode
        const val ARRAY_LOAD = 12 // ??
        const val ARRAY_STORE = 13 // ??
        const val GETFIELD = 14 // ??
        const val PUTFIELD = 15 // ??
        const val GETSTATIC = 16 // ??
        const val PUTSTATIC = 17 // ??

        const val INVOKE = 20 // MethodInsnNode
        const val INVOKESTATIC = 21 // MethodInsnNode
        const val INVOKEDYNAMIC = 22 // InvokeDynamicInsnNode
        const val NEW = 23 // ??
        const val NEWARRAY = 24 // ??
        const val THROW = 25 // ??
        const val RETURN = 26 // OpInsnNode
        const val ARETURN = 27 // -
        const val CAST = 28
        const val INSTANCEOF = 29

        const val POP = 30 // OpInsnNode
        const val DUP = 31 // -
        const val DUP_X1 = 32 // -
        const val DUP_X2 = 33 // -
        const val DUP2 = 34 // -
        const val DUP2_X1 = 35 // -
        const val DUP2_X2 = 36 // -
        const val SWAP = 37 // -

        const val ADD = 40 // -
        const val SUB = 41 // -
        const val MUL = 42 // -
        const val DIV = 43 // -
        const val REM = 44 // -
        const val NEG = 45 // -
        const val SHL = 46 // -
        const val SHR = 47 // -
        const val USHR = 48 // -
        const val AND = 49 // -
        const val OR = 50 // -
        const val XOR = 51 // -

        const val IINC = 60 // ??
        const val CMP = 61 // JumpInsnNode
        const val MULTINEWARRAY = 62 // ??

        const val IFEQ = 70 // JumpInsnNode
        const val IFNE = 71 // -
        const val IF_CMPLT = 72 // -
        const val IF_CMPGT = 73 // -
        const val IF_CMPGE = 74 // -
        const val IF_CMPLE = 75 // -
        const val IF_CMPEQ = 76 // -
        const val IF_CMPNE = 77 // -
        const val GOTO = 78 // GotoInsnNode
        const val SWITCH = 79
    }
}