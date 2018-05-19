package bytecode.insns

interface Instruction {
    val opcode: Int

    companion object {
        const val LABEL = 1

        const val NOOP = 0 // OpInstruction
        const val PUSH = 1 // -
        const val ICONST = 2 // IntInstruction
        const val LCONST = 3 // LongInstruction
        const val FCONST = 4 // FloatInstruction
        const val DCONST = 5 // DoubleInstruction

        const val LOCAL_LOAD = 10 // LoadInstruction
        const val LOCAL_STORE = 11 // StoreInstruction
        const val ARRAY_LOAD = 12 // ??
        const val ARRAY_STORE = 13 // ??
        const val GETFIELD = 14 // ??
        const val PUTFIELD = 15 // ??
        const val GETSTATIC = 16 // ??
        const val PUTSTATIC = 17 // ??

        const val INVOKE = 20 // MethodInstruction
        const val INVOKESTATIC = 21 // MethodInstruction
        const val INVOKEDYNAMIC = 22 // InvokeDynamicInstruction
        const val NEW = 23 // ??
        const val NEWARRAY = 24 // ??
        const val THROW = 25 // ??
        const val RETURN = 26 // OpInstruction
        const val ARETURN = 27 // -
        const val CAST = 28
        const val INSTANCEOF = 29

        const val POP = 30 // OpInstruction
        const val DUP = 31 // -
        const val DUP_X1 = 32 // -
        const val DUP_X2 = 33 // -
        const val DUP2 = 34 // -
        const val DUP2_X1 = 35 // -
        const val DUP2_X2 = 36 // -
        const val SWAP = 37 // -


        // TODO delete
        // BinaryOperation
        const val ADD = 40 // -
        const val SUB = 41 // -
        const val MUL = 42 // -
        const val DIV = 43 // -
        const val REM = 44 // -
        const val SHL = 46 // -
        const val SHR = 47 // -
        const val USHR = 48 // -
        const val AND = 49 // -
        const val OR = 50 // -
        const val XOR = 51 // -

        const val NEG = 45 // -
        const val IINC = 60 // ??
        const val CMP = 61 // JumpInstruction
        const val MULTINEWARRAY = 62 // ??

        const val IFEQ = 70 // JumpInstruction
        const val IFNE = 71 // -
        const val IF_CMPLT = 72 // -
        const val IF_CMPGT = 73 // -
        const val IF_CMPGE = 74 // -
        const val IF_CMPLE = 75 // -
        const val IF_CMPEQ = 76 // -
        const val IF_CMPNE = 77 // -
        const val GOTO = 78 // GotoInstruction
        const val SWITCH = 79
    }
}