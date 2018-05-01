package bytecode.insns

abstract class InsnNode(val opcode: Int) {
    companion object {
        val LABEL = -1

        val NOOP = 0
        val PUSH = 1
        val ICONST = 2
        val LCONST = 3
        val FCONST = 4
        val DCONST = 5

        val LOCAL_LOAD = 10
        val LOCAL_STORE = 11
        val ARRAY_LOAD = 12
        val ARRAY_STORE = 13
        val GETFIELD = 14
        val PUTFIELD = 15
        val GETSTATIC = 16
        val PUTSTATIC = 17

        val INVOKE = 20
        val INVOKESTATIC = 21
        val INVOKEDYNAMIC = 22
        val NEW = 23
        val NEWARRAY = 24
        val THROW = 25
        val RETURN = 26
        val ARETURN = 27
        val CAST = 28
        val INSTANCEOF = 29

        val POP = 30
        val DUP = 31
        val DUP_X1 = 32
        val DUP_X2 = 33
        val DUP2 = 34
        val DUP2_X1 = 35
        val DUP2_X2 = 36
        val SWAP = 37

        val ADD = 40
        val SUB = 41
        val MUL = 42
        val DIV = 43
        val REM = 44
        val NEG = 45
        val SHL = 46
        val SHR = 47
        val USHR = 48
        val AND = 49
        val OR = 50
        val XOR = 51

        val IINC = 60
        val CMP = 61
        val MULTINEWARRAY = 62

        val IFEQ = 70
        val IFNE = 71
        val IF_CMPLT = 72
        val IF_CMPGT = 73
        val IF_CMPGE = 74
        val IF_CMPLE = 75
        val IF_CMPEQ = 76
        val IF_CMPNE = 77
        val GOTO = 78
        val SWITCH = 79
    }
}