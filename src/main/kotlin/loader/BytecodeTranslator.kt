package loader


import exceptions.SourceFormatException
import invoke.InstanceMethodInvoke
import loader.ConstantPool.*
import ast.signature.ClassTypeSignature
import java.util.*
import ir.*
import method.BootstrapMethod

class BytecodeTranslator {

    fun createIR(code: ByteArray, pool: ConstantPool,
                 bootstrap_methods: List<BootstrapMethod>): InsnBlock {
        val block = InsnBlock()
        val insn_starts = ArrayList<Int>()

        var i = 0
        while (i < code.size) {
            val opcode_index = i
            insn_starts.add(opcode_index)
            val next = code[i++].toInt() and 0xFF
            when (next) {
            // NOP
                0 -> block.append(OpInsn(Insn.NOOP))

            // ACONST_NULL
                1 -> block.append(LdcInsn(Insn.PUSH, null))

            // ICONST_M1
                2 -> block.append(IntInsn(Insn.ICONST, -1))

            // ICONST_0
                3 -> block.append(IntInsn(Insn.ICONST, 0))

            // ICONST_1
                4 -> block.append(IntInsn(Insn.ICONST, 1))

            // ICONST_2
                5 -> block.append(IntInsn(Insn.ICONST, 2))

            // ICONST_3
                6 -> block.append(IntInsn(Insn.ICONST, 3))

            // ICONST_4
                7 -> block.append(IntInsn(Insn.ICONST, 4))

            // ICONST_5
                8 -> block.append(IntInsn(Insn.ICONST, 5))

            // LCONST_0
                9 -> block.append(LongInsn(Insn.LCONST, 0))

            // LCONST_1
                10 -> block.append(LongInsn(Insn.LCONST, 1))

            // FCONST_0
                11 -> block.append(FloatInsn(Insn.FCONST, 0.toFloat()))

            // FCONST_1
                12 -> block.append(FloatInsn(Insn.FCONST, 1.toFloat()))

            // FCONST_2
                13 -> block.append(FloatInsn(Insn.FCONST, 2.toFloat()))

            // DCONST_0
                14 -> block.append(DoubleInsn(Insn.DCONST, 0.0))

            // DCONST_1
                15 -> block.append(DoubleInsn(Insn.DCONST, 1.0))

            // BIPUSH
                16 -> {
                    val value = code[i++].toInt()
                    block.append(IntInsn(Insn.ICONST, value))
                }
                17 -> {// SIPUSH
                    val value = (code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF))
                    block.append(IntInsn(Insn.ICONST, value))
                }
                18 -> {// LDC
                    val index = code[i++].toInt() and 0xFF
                    val entry = pool.getEntry(index)
                    if (entry is IntEntry) {
                        block.append(IntInsn(Insn.ICONST, (entry as IntEntry).value))
                    } else if (entry is FloatEntry) {
                        block.append(FloatInsn(Insn.FCONST, (entry as FloatEntry).value))
                    } else if (entry is StringEntry) {
                        block.append(LdcInsn(Insn.PUSH, (entry as StringEntry).value))
                    } else if (entry is ClassEntry) {
                        var type = (entry as ClassEntry).name
                        if (!type.startsWith("[")) {
                            type = "L$type;"
                        }
                        block.append(LdcInsn(Insn.PUSH, ClassTypeSignature.of(type)))
                    } else {
                        throw IllegalStateException("Unsupported constant " +
                                "pool entry type in LDC node " + entry.javaClass
                                .getSimpleName())
                    }
                }
            // LDC_W
                19 -> {
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val entry = pool.getEntry(index)
                    if (entry is IntEntry) {
                        block.append(IntInsn(Insn.ICONST, entry.value))
                    } else if (entry is FloatEntry) {
                        block.append(FloatInsn(Insn.FCONST, entry.value))
                    } else if (entry is StringEntry) {
                        block.append(LdcInsn(Insn.PUSH, entry.value))
                    } else if (entry is ClassEntry) {
                        block.append(LdcInsn(Insn.PUSH, ClassTypeSignature.of("L" + entry.name + ";")))
                    } else {
                        throw IllegalStateException("Unsupported constant " +
                                "pool entry type in LDC node " + entry.javaClass.simpleName)
                    }
                }
            // LDC2_W
                20 -> {
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val entry = pool.getEntry(index)
                    if (entry is LongEntry) {
                        block.append(LongInsn(Insn.LCONST, entry.value))
                    } else if (entry is DoubleEntry) {
                        block.append(DoubleInsn(Insn.DCONST, entry.value))
                    } else {
                        throw IllegalStateException("Unsupported constant " +
                                "pool entry type in LDC node " + entry.javaClass.simpleName)
                    }
                }
            // ILOAD
                21,
                    // LLOAD
                22,
                    // FLOAD
                23,
                    // DLOAD
                24,
                    // ALOAD
                25 -> {
                    val `val` = code[i++].toInt() and 0xFF
                    block.append(IntInsn(Insn.LOCAL_LOAD, `val`))
                }
            // ILOAD_0
                26,
                    // LLOAD_0
                30,
                    // FLOAD_0
                34,
                    // DLOAD_0
                38,
                    // ALOAD_0
                42 -> block.append(IntInsn(Insn.LOCAL_LOAD, 0))

            // ILOAD_1
                27,
                    // LLOAD_1
                31,
                    // FLOAD_1
                35,
                    // DLOAD_1
                39,
                    // ALOAD_1
                43 -> block.append(IntInsn(Insn.LOCAL_LOAD, 1))

            // ILOAD_2
                28,
                32 // LLOAD_2
                    , 36 // FLOAD_2
                    , 40 // DLOAD_2
                    , 44 // ALOAD_2
                -> block.append(IntInsn(Insn.LOCAL_LOAD, 2))
                29 // ILOAD_3
                    , 33 // LLOAD_3
                    , 37 // FLOAD_3
                    , 41 // DLOAD_3
                    , 45 // ALOAD_3
                -> block.append(IntInsn(Insn.LOCAL_LOAD, 3))
                46 // IALOAD
                    , 47 // LALOAD
                    , 48 // FALOAD
                    , 49 // DALOAD
                    , 50 // AALOAD
                    , 51 // BALOAD
                    , 52 // CALOAD
                    , 53 // SALOAD
                -> block.append(OpInsn(Insn.ARRAY_LOAD))
                54 -> { // ISTORE
                    val local = code[i++].toInt() and 0xFF
                    block.append(IntInsn(Insn.LOCAL_STORE, local))
                }
                55 // LSTORE
                    , 56 // FSTORE
                    , 57 // DSTORE
                    , 58 -> { // ASTORE
                    val `val` = code[i++].toInt() and 0xFF
                    block.append(IntInsn(Insn.LOCAL_STORE, `val`))
                }
                59 // ISTORE_0
                    , 63 // LSTORE_0
                    , 67 // FSTORE_0
                    , 71 // DSTORE_0
                    , 75 // ASTORE_0
                -> block.append(IntInsn(Insn.LOCAL_STORE, 0))
                60 // ISTORE_1
                    , 64 // LSTORE_1
                    , 68 // FSTORE_1
                    , 72 // DSTORE_1
                    , 76 // ASTORE_1
                -> block.append(IntInsn(Insn.LOCAL_STORE, 1))
                61 // ISTORE_2
                    , 65 // LSTORE_2
                    , 69 // FSTORE_2
                    , 73 // DSTORE_2
                    , 77 // ASTORE_2
                -> block.append(IntInsn(Insn.LOCAL_STORE, 2))
                62 // ISTORE_3
                    , 66 // LSTORE_3
                    , 70 // FSTORE_3
                    , 74 // DSTORE_3
                    , 78 // ASTORE_3
                -> block.append(IntInsn(Insn.LOCAL_STORE, 3))
                79 // IASTORE
                    , 80 // LASTORE
                    , 81 // FASTORE
                    , 82 // DASTORE
                    , 83 // AASTORE
                    , 84 // BASTORE
                    , 85 // CASTORE
                    , 86 // SASTORE
                -> block.append(OpInsn(Insn.ARRAY_STORE))
                87 // POP
                -> block.append(OpInsn(Insn.POP))
                88 // POP2
                -> {
                    block.append(OpInsn(Insn.POP))
                    insn_starts.add(opcode_index)
                    block.append(OpInsn(Insn.POP))
                }
                89 // DUP
                -> block.append(OpInsn(Insn.DUP))
                90 // DUP_X1
                -> block.append(OpInsn(Insn.DUP_X1))
                91 // DUP_X2
                -> block.append(OpInsn(Insn.DUP_X2))
                92 // DUP2
                -> block.append(OpInsn(Insn.DUP2))
                93 // DUP2_X1
                -> block.append(OpInsn(Insn.DUP2_X1))
                94 // DUP2_X2
                -> block.append(OpInsn(Insn.DUP2_X2))
                95 // SWAP
                -> block.append(OpInsn(Insn.SWAP))
                96 // IADD
                    , 97 // LADD
                    , 98 // FADD
                    , 99 // DADD
                -> block.append(OpInsn(Insn.ADD))
                100 // ISUB
                    , 101 // LSUB
                    , 102 // FSUB
                    , 103 // DSUB
                -> block.append(OpInsn(Insn.SUB))
                104 // IMUL
                    , 105 // LMUL
                    , 106 // FMUL
                    , 107 // DMUL
                -> block.append(OpInsn(Insn.MUL))
                108 // IDIV
                    , 109 // LDIV
                    , 110 // FDIV
                    , 111 // DDIV
                -> block.append(OpInsn(Insn.DIV))
                112 // IREM
                    , 113 // LREM
                    , 114 // FREM
                    , 115 // DREM
                -> block.append(OpInsn(Insn.REM))
                116 // INEG
                    , 117 // LNEG
                    , 118 // FNEG
                    , 119 // DNEG
                -> block.append(OpInsn(Insn.NEG))
                120 // ISHL
                    , 121 // LSHL
                -> block.append(OpInsn(Insn.SHL))
                122 // ISHR
                    , 123 // LSHR
                -> block.append(OpInsn(Insn.SHR))
                124 // IUSHR
                    , 125 // LUSHR
                -> block.append(OpInsn(Insn.USHR))
                126 // IAND
                    , 127 // LAND
                -> block.append(OpInsn(Insn.AND))
                128 // IOR
                    , 129 // LOR
                -> block.append(OpInsn(Insn.OR))
                130 // IXOR
                    , 131 // LXOR
                -> block.append(OpInsn(Insn.XOR))
                132 -> {// IINC
                    val local = code[i++].toInt() and 0xFF
                    val incr = code[i++].toInt()
                    block.append(VarIntInsn(Insn.IINC, local, incr))
                }
                136 // L2I
                    , 139 // F2I
                    , 142 // D2I
                -> block.append(TypeInsn(Insn.CAST, "I"))
                133 // I2L
                    , 140 // F2L
                    , 143 // D2L
                -> block.append(TypeInsn(Insn.CAST, "J"))
                134 // I2F
                    , 137 // L2F
                    , 144 // D2F
                -> block.append(TypeInsn(Insn.CAST, "F"))
                135 // I2D
                    , 138 // L2D
                    , 141 // F2D
                -> block.append(TypeInsn(Insn.CAST, "D"))
                145 // I2B
                -> block.append(TypeInsn(Insn.CAST, "B"))
                146 // I2C
                -> block.append(TypeInsn(Insn.CAST, "C"))
                147 // I2S
                -> block.append(TypeInsn(Insn.CAST, "S"))
                148 // LCMP
                    , 149 // FCMPL
                    , 150 // FCMPG
                    , 151 // DCMPL
                    , 152 // DCMPG
                -> block.append(OpInsn(Insn.CMP))
                153 -> {// IFEQ
                    val index = (code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IFEQ, opcode_index + index))
                }
                154 -> {// IFNE
                    val index = (code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IFNE, opcode_index + index))
                }
                155 -> {// IFLT
                    block.append(IntInsn(Insn.ICONST, 0))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPLT, opcode_index + index))
                }
                156 -> {// IFGE
                    block.append(IntInsn(Insn.ICONST, 0))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPGE, opcode_index + index))
                }
                157 -> {// IFGT
                    block.append(IntInsn(Insn.ICONST, 0))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPGT, opcode_index + index))
                }
                158 -> {// IFLE
                    block.append(IntInsn(Insn.ICONST, 0))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPLE, opcode_index + index))
                }
                159 -> {// IF_ICMPEQ
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPEQ, opcode_index + index))
                }
                160 -> {// IF_ICMPNE
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPNE, opcode_index + index))
                }
                161 -> {// IF_ICMPLT
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPLT, opcode_index + index))
                }
                162 -> {// IF_ICMPGE
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPGE, opcode_index + index))
                }
                163 -> {// IF_ICMPGT
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPGT, opcode_index + index))
                }
                164 -> {// IF_ICMPLE
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPLE, opcode_index + index))
                }
                165 -> {// IF_ACMPEQ
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPEQ, opcode_index + index))
                }
                166 -> {// IF_ACMPNE
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPNE, opcode_index + index))
                }
                167 -> {// GOTO
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.GOTO, opcode_index + index))
                }
                168 // JSR
                    , 169 // RET
                -> throw SourceFormatException("Unsupported java opcode: $next")
                170 -> {// TABLESWITCH
                    while (i % 4 != 0) {
                        i++
                    }
                    val def = opcode_index + readInt(code, i)
                    i += 4
                    val low = readInt(code, i)
                    i += 4
                    val high = readInt(code, i)
                    i += 4
                    val targets = HashMap<Int, Int>()
                    for (j in 0 until high - low + 1) {
                        targets[low + j] = opcode_index + readInt(code, i)
                        i += 4
                    }
                    block.append(SwitchInsn(Insn.SWITCH, targets, def))
                }
                171 -> {// LOOKUPSWITCH
                    while (i % 4 != 0) {
                        i++
                    }
                    val def = opcode_index + readInt(code, i)
                    i += 4
                    val npairs = readInt(code, i)
                    i += 4
                    val targets = HashMap<Int, Int>()
                    for (j in 0 until npairs) {
                        val key = readInt(code, i)
                        i += 4
                        targets[key] = opcode_index + readInt(code, i)
                        i += 4
                    }
                    block.append(SwitchInsn(Insn.SWITCH, targets, def))
                }
                172 // IRETURN
                    , 173 // LRETURN
                    , 174 // FRETURN
                    , 175 // DRETURN
                    , 176 // ARETURN
                -> block.append(OpInsn(Insn.ARETURN))
                177 // RETURN
                -> block.append(OpInsn(Insn.RETURN))
                178 -> { // GETSTATIC
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getFieldRef(index)
                    block.append(FieldInsn(Insn.GETSTATIC, ref.cls, ref.name, ref.type_name))
                }
                179 -> { // PUTSTATIC
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getFieldRef(index)
                    block.append(FieldInsn(Insn.PUTSTATIC, ref.cls, ref.name, ref.type_name))
                }
                180 -> { // GETFIELD
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getFieldRef(index)
                    block.append(FieldInsn(Insn.GETFIELD, ref.cls, ref.name, ref.type_name))
                }
                181 -> { // PUTFIELD
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getFieldRef(index)
                    block.append(FieldInsn(Insn.PUTFIELD, ref.cls, ref.name, ref.type_name))
                }
                182 // INVOKEVIRTUAL
                    , 183 -> { // INVOKESPECIAL
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getMethodRef(index)
                    val t = if (next == 182) InstanceMethodInvoke.Type.VIRTUAL else InstanceMethodInvoke.Type.SPECIAL
                    block.append(InvokeInsn(Insn.INVOKE, t, ref.cls, ref.name, ref.type_name))
                }
                184 -> { // INVOKESTATIC
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getMethodRef(index)
                    block.append(InvokeInsn(Insn.INVOKESTATIC, null, ref.cls, ref.name, ref.type_name))
                }
                185 -> {// INVOKEINTERFACE
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    // skip count and constant 0 (historical)
                    i += 2
                    val ref = pool.getInterfaceMethodRef(index)
                    block.append(InvokeInsn(Insn.INVOKE, InstanceMethodInvoke.Type.INTERFACE, ref.cls, ref.name, ref.type_name))
                }
                186 -> {// INVOKEDYNAMIC
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    // skip constant 0 (historical)
                    i += 2
                    val handle = pool.getInvokeDynamic(index)
                    val bsm = bootstrap_methods[handle.bootstrap_index]
                    val bsmArg = pool.getMethodRef((bsm.arguments[1] as MethodHandleEntry).reference_index)
                    block.append(InvokeDynamicInsn(Insn.INVOKEDYNAMIC, "L" + bsmArg.cls + ";", bsmArg.name, bsmArg.type_name, handle.name, handle.type_name,
                            bsmArg.type === ConstantPool.EntryType.INTERFACE_METHOD_REF))
                }
                187 -> {// NEW
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getClass(index)
                    block.append(TypeInsn(Insn.NEW, "L" + ref.name + ";"))
                }
                188 -> {// NEWARRAY
                    val atype = code[i++].toInt()
                    val type = when (atype) {
                        4 -> "Z" // T_BOOLEAN
                        5 -> "C" // T_CHAR
                        6 -> "F" // T_FLOAT
                        7 -> "D" // T_DOUBLE
                        8 -> "B" // T_BYTE
                        9 -> "S" // T_SHORT
                        10 -> "I" // T_INT
                        11 -> "J" // T_LONG
                        else -> throw SourceFormatException("Unsupported NEWARRAY type value: $atype")
                    }
                    block.append(TypeInsn(Insn.NEWARRAY, type))
                }
                189 -> {// ANEWARRAY
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getClass(index)
                    var desc = ref.name
                    if (!desc.startsWith("[") && (desc.length > 1 || "BSIJFDCZ".indexOf(desc[0]) == -1)) {
                        desc = "L$desc;"
                    }
                    block.append(TypeInsn(Insn.NEWARRAY, desc))
                }
                190 // ARRAYLENGTH
                -> block.append(FieldInsn(Insn.GETFIELD, "", "length", "I"))
                191 // ATHROW
                -> block.append(OpInsn(Insn.THROW))
                192 -> {// CHECKCAST
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getClass(index)
                    var desc = ref.name
                    if (!desc.startsWith("[")) {
                        desc = "L$desc;"
                    }
                    block.append(TypeInsn(Insn.CAST, desc))
                }
                193 -> {// INSTANCEOF
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getClass(index)
                    block.append(TypeInsn(Insn.INSTANCEOF, "L" + ref.name + ";"))
                }
                194 // MONITORENTER
                    , 195 // MONITOREXIT
                    , 196 // WIDE
                -> throw SourceFormatException("Unsupported java opcode: $next")
                197 -> {// MULTINEWARRAY
                    val index = code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)
                    val ref = pool.getClass(index)
                    val dims = code[i++].toInt() and 0xFF
                    block.append(TypeIntInsn(Insn.MULTINEWARRAY, ref.name, dims))
                }

            // IFNULL
                198 -> {
                    block.append(LdcInsn(Insn.PUSH, null))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPEQ, opcode_index + index))
                }

            // IFNONNULL
                199 -> {
                    block.append(LdcInsn(Insn.PUSH, null))
                    insn_starts.add(opcode_index)
                    val index = (code[i++].toInt() and 0xFF shl 8 or
                            (code[i++].toInt() and 0xFF)).toShort()
                    block.append(JumpInsn(Insn.IF_CMPNE, opcode_index + index))
                }
            // GOTO_W
                200,
                    // JSR_W
                201 -> {
                } // throw SourceFormatException("Unsupported java  opcode: $next")
                else -> {
                } // throw SourceFormatException("Unknown java opcode: $next")
            }
        }

        for (insn in block) {
            if (insn is JumpInsn) {
                val jump = insn as JumpInsn
                jump.target = insn_starts.indexOf(jump.target)
            } else if (insn is SwitchInsn) {
                val sw = insn as SwitchInsn
                sw.default = insn_starts.indexOf(sw.default)
                val new_targets = HashMap<Int, Int>()
                for (e in sw.targets) {
                    new_targets[e.key] = insn_starts.indexOf(e.value)
                }
                sw.targets.clear()
                sw.targets.putAll(new_targets)
            }
        }
// TODO add locals
//        for (region in catch_regions) {
//            val start_pc = insn_starts.indexOf(region.getStart())
//            val end_pc = insn_starts.indexOf(region.getEnd())
//            val catch_pc = insn_starts.indexOf(region.getCatch())
//            block.getCatchRegions().add(TryCatchRegion(start_pc, end_pc, catch_pc, region.getException()))
//        }
// TODO add locals
//        locals.bakeInstances(insn_starts)

        return block
    }

    private fun readInt(code: ByteArray, i: Int): Int {
        var i = i
        val byte1 = code[i++].toInt() and 0xFF
        val byte2 = code[i++].toInt() and 0xFF
        val byte3 = code[i++].toInt() and 0xFF
        val byte4 = code[i++].toInt() and 0xFF
        return byte1 shl 24 or (byte2 shl 16) or (byte3 shl 8) or byte4
    }

}
