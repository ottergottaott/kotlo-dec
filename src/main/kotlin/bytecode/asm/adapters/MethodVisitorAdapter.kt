package bytecode.asm.adapters

import bytecode.insns.*
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodNode

class MethodVisitorAdapter(access: Int, name: String?,
                           desc: String?, signature: String?,
                           exceptions: Array<out String>?) : MethodNode(ASM5, access, name, desc, signature, exceptions) {
    val myInsns: MutableList<Instruction> = ArrayList()

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        when (opcode) {
            ILOAD, LLOAD, FLOAD,
            DLOAD, ALOAD, IALOAD,
            LALOAD, FALOAD, DALOAD,
            AALOAD, BALOAD, CALOAD,
            SALOAD -> myInsns.add(LoadInstruction(Instruction.LOCAL_LOAD, `var`))
            ISTORE, LSTORE, FSTORE,
            DSTORE, ASTORE -> myInsns.add(StoreInstruction(Instruction.LOCAL_STORE, `var`))
        }
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        val target = LabelInstruction(LabelNode(label))

        when (opcode) {
            GOTO -> {
                myInsns.add(GotoInstruction(Instruction.GOTO, target))
            }
            IFEQ -> {
                myInsns.add(JumpInstruction(Instruction.IFEQ, target))
            }
            IFNE -> {
                myInsns.add(JumpInstruction(Instruction.IFNE, target))
            }
            LCMP, FCMPL, FCMPG,
            DCMPL, DCMPG -> {
                myInsns.add(JumpInstruction(Instruction.CMP, target))
            }

            IFLE -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 0))
                myInsns.add(JumpInstruction(Instruction.IF_CMPLE, target))
            }

            IFLT -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 0))
                myInsns.add(JumpInstruction(Instruction.IF_CMPLT, target))
            }

            IFGT -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 0))
                myInsns.add(JumpInstruction(Instruction.IF_CMPGT, target))
            }

            IFGE -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 0))
                myInsns.add(JumpInstruction(Instruction.IF_CMPGE, target))
            }

            IF_ICMPNE, IF_ACMPNE -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPNE, target))
            }

            IF_ICMPEQ, IF_ACMPEQ -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPEQ, target))
            }
            IF_ICMPGE -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPGE, target))
            }

            IF_ICMPGT -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPGT, target))
            }

            IF_ICMPLE -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPLE, target))
            }

            IF_ICMPLT -> {
                myInsns.add(JumpInstruction(Instruction.IF_CMPLT, target))
            }

            IFNULL -> {
                myInsns.add(LdcInstruction(Instruction.PUSH, null))
                myInsns.add(JumpInstruction(Instruction.IF_CMPEQ, target))
            }

            IFNONNULL -> {
                myInsns.add(LdcInstruction(Instruction.PUSH, null))
                myInsns.add(JumpInstruction(Instruction.IF_CMPNE, target))
            }
        }
    }


    override fun visitInvokeDynamicInsn(name: String?, desc: String?, bsm: Handle?, vararg bsmArgs: Any?) {
        InvokeDynamicInstruction(Instruction.INVOKEDYNAMIC, name, desc, bsm, listOf(*bsmArgs))
    }

    override fun visitLabel(label: Label) {
        val target = LabelInstruction(LabelNode(label))
        myInsns.add(target)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        when (opcode) {
            INVOKESPECIAL, INVOKEVIRTUAL, INVOKEINTERFACE -> {
                myInsns.add(MethodInstruction(Instruction.INVOKE, owner, name, desc, itf))
            }

            INVOKESTATIC -> {
                myInsns.add(MethodInstruction(Instruction.INVOKESTATIC, owner, name, desc, itf))
            }
        }
    }

    override fun visitInsn(opcode: Int) {
        when (opcode) {
            NOP -> {
                myInsns.add(OpInstruction(Instruction.NOOP))
            }

            POP -> {
                myInsns.add(OpInstruction(Instruction.POP))
            }
            POP2 -> {
                myInsns.add(OpInstruction(Instruction.POP))
                myInsns.add(OpInstruction(Instruction.POP))
            }

            DUP -> {
                myInsns.add(OpInstruction(Instruction.DUP))
            }

            DUP_X1 -> {
                myInsns.add(OpInstruction(Instruction.DUP_X1))
            }

            DUP_X2 -> {
                myInsns.add(OpInstruction(Instruction.DUP_X2))
            }
            DUP2 -> {
                myInsns.add(OpInstruction(Instruction.DUP2))
            }

            DUP2_X1 -> {
                myInsns.add(OpInstruction(Instruction.DUP2_X1))

            }

            DUP2_X2 -> {
                myInsns.add(OpInstruction(Instruction.DUP2_X2))
            }
            ICONST_M1 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, -1))
            }

            ICONST_0 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 0))
            }
            ICONST_1 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 1))
            }
            ICONST_2 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 2))
            }
            ICONST_3 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 3))
            }
            ICONST_4 -> {
                myInsns.add(IntInstruction(Instruction.ICONST, 4))
            }

            ICONST_5 -> {
                myInsns.add(LongInstruction(Instruction.ICONST, 5))
            }
            LCONST_0 -> {
                myInsns.add(LongInstruction(Instruction.LCONST, 0))
            }
            LCONST_1 -> {
                myInsns.add(LongInstruction(Instruction.LCONST, 1))
            }
            FCONST_0 -> {
                myInsns.add(FloatInstruction(Instruction.FCONST, 0f))
            }

            FCONST_1 -> {
                myInsns.add(FloatInstruction(Instruction.FCONST, 1f))
            }
            FCONST_2 -> {
                myInsns.add(FloatInstruction(Instruction.FCONST, 2f))
            }

            DCONST_0 -> {
                myInsns.add(DoubleInstruction(Instruction.DCONST, 0.0))
            }

            DCONST_1 -> {
                myInsns.add(DoubleInstruction(Instruction.DCONST, 1.0))
            }

            IADD, LADD, FADD, DADD -> {
                myInsns.add(OpInstruction(Instruction.ADD))
            }

            ISUB, LSUB, FSUB, DSUB -> {
                myInsns.add(OpInstruction(Instruction.SUB))
            }

            IMUL, LMUL, FMUL, DMUL -> {
                myInsns.add(OpInstruction(Instruction.MUL))
            }
            IDIV, LDIV, FDIV, DDIV -> {
                myInsns.add(OpInstruction(Instruction.DIV))
            }

            IREM, LREM, FREM, DREM -> {
                myInsns.add(OpInstruction(Instruction.REM))
            }

            INEG, LNEG, FNEG, DNEG -> {
                myInsns.add(OpInstruction(Instruction.NEG))
            }
            ISHL, LSHL -> {
                myInsns.add(OpInstruction(Instruction.SHL))
            }
            ISHR, LSHR -> {
                myInsns.add(OpInstruction(Instruction.SHR))
            }
            IUSHR, LUSHR -> {
                myInsns.add(OpInstruction(Instruction.USHR))
            }
            IAND, LAND -> {
                myInsns.add(OpInstruction(Instruction.AND))
            }
            IOR, LOR -> {
                myInsns.add(OpInstruction(Instruction.OR))
            }
            IXOR, LXOR -> {
                myInsns.add(OpInstruction(Instruction.XOR))
            }
            IRETURN, LRETURN, FRETURN,
            DRETURN, ARETURN -> {
                myInsns.add(OpInstruction(Instruction.ARETURN))
            }

            SWAP -> {
                myInsns.add(OpInstruction(Instruction.SWAP))
            }

            RETURN -> {
                myInsns.add(OpInstruction(Instruction.RETURN))
            }
        }
    }

    override fun visitLdcInsn(cst: Any?) {
        myInsns.add(LdcInstruction(Instruction.PUSH, cst))
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        when (opcode) {
            BIPUSH, SIPUSH -> myInsns.add(IntInstruction(Instruction.ICONST, operand))
        // TODO NEWARRAY
        }
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
//        super.visitTypeInsn(opcode, type)
        // TODO CASTS
    }

    override fun visitIincInsn(`var`: Int, increment: Int) {
        myInsns.add(IncInstruction(Instruction.IINC, `var`, increment))
    }



}
