package bytecode.asm.adapters

import bytecode.insns.*
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodNode

class MethodVisitorAdapter(access: Int, name: String?,
                           desc: String?, signature: String?,
                           exceptions: Array<out String>?) :

        MethodNode(ASM5, access, name, desc, signature, exceptions) {
    val myInsns: MutableList<InsnNode> = ArrayList()

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        when (opcode) {
            ILOAD, LLOAD, FLOAD,
            DLOAD, ALOAD, IALOAD,
            LALOAD, FALOAD, DALOAD,
            AALOAD, BALOAD, CALOAD,
            SALOAD -> myInsns.add(LoadInsnNode(InsnNode.LOCAL_LOAD, `var`))
            ISTORE, LSTORE, FSTORE,
            DSTORE, ASTORE -> myInsns.add(StoreInsnNode(InsnNode.LOCAL_STORE, `var`))
        }
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        val target = LabelInsnNode(LabelNode(label))

        when (opcode) {
            GOTO -> {
                myInsns.add(GotoInsnNode(InsnNode.GOTO, target))
            }
            IFEQ -> {
                myInsns.add(JumpInsnNode(InsnNode.IFEQ, target))
            }
            IFNE -> {
                myInsns.add(JumpInsnNode(InsnNode.IFNE, target))
            }
            LCMP, FCMPL, FCMPG,
            DCMPL, DCMPG -> {
                myInsns.add(JumpInsnNode(InsnNode.CMP, target))
            }

            IFLE -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 0))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPLE, target))
            }

            IFLT -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 0))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPLT, target))
            }

            IFGT -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 0))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPGT, target))
            }

            IFGE -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 0))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPGE, target))
            }

            IF_ICMPNE, IF_ACMPNE -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPNE, target))
            }

            IF_ICMPEQ, IF_ACMPEQ -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPEQ, target))
            }
            IF_ICMPGE -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPGE, target))
            }

            IF_ICMPGT -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPGT, target))
            }

            IF_ICMPLE -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPLE, target))
            }

            IF_ICMPLT -> {
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPLT, target))
            }

            IFNULL -> {
                myInsns.add(LdcInsnNode(InsnNode.PUSH, null))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPEQ, target))
            }

            IFNONNULL -> {
                myInsns.add(LdcInsnNode(InsnNode.PUSH, null))
                myInsns.add(JumpInsnNode(InsnNode.IF_CMPNE, target))
            }
        }
    }


    override fun visitInvokeDynamicInsn(name: String?, desc: String?, bsm: Handle?, vararg bsmArgs: Any?) {
        InvokeDynamicInsnNode(InsnNode.INVOKEDYNAMIC, name, desc, bsm, bsmArgs)
    }

    override fun visitLabel(label: Label) {
        val target = LabelInsnNode(LabelNode(label))
        myInsns.add(target)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        when (opcode) {
            INVOKESPECIAL, INVOKEVIRTUAL, INVOKEINTERFACE -> {
                myInsns.add(MethodInsnNode(InsnNode.INVOKE, owner, name, desc, itf))
            }

            INVOKESTATIC -> {
                myInsns.add(MethodInsnNode(InsnNode.INVOKESTATIC, owner, name, desc, itf))
            }
        }
    }

    override fun visitInsn(opcode: Int) {
        when (opcode) {
            NOP -> {
                myInsns.add(OpInsnNode(InsnNode.NOOP))
            }

            POP -> {
                myInsns.add(OpInsnNode(InsnNode.POP))
            }
            POP2 -> {
                myInsns.add(OpInsnNode(InsnNode.POP))
                myInsns.add(OpInsnNode(InsnNode.POP))
            }

            DUP -> {
                myInsns.add(OpInsnNode(InsnNode.DUP))
            }

            DUP_X1 -> {
                myInsns.add(OpInsnNode(InsnNode.DUP_X1))
            }

            DUP_X2 -> {
                myInsns.add(OpInsnNode(InsnNode.DUP_X2))
            }
            DUP2 -> {
                myInsns.add(OpInsnNode(InsnNode.DUP2))
            }

            DUP2_X1 -> {
                myInsns.add(OpInsnNode(InsnNode.DUP2_X1))

            }

            DUP2_X2 -> {
                myInsns.add(OpInsnNode(InsnNode.DUP2_X2))
            }
            ICONST_M1 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, -1))
            }

            ICONST_0 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 0))
            }
            ICONST_1 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 1))
            }
            ICONST_2 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 2))
            }
            ICONST_3 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 3))
            }
            ICONST_4 -> {
                myInsns.add(IntInsnNode(InsnNode.ICONST, 4))
            }

            ICONST_5 -> {
                myInsns.add(LongInsnNode(InsnNode.ICONST, 5))
            }
            LCONST_0 -> {
                myInsns.add(LongInsnNode(InsnNode.LCONST, 0))
            }
            LCONST_1 -> {
                myInsns.add(LongInsnNode(InsnNode.LCONST, 1))
            }
            FCONST_0 -> {
                myInsns.add(FloatInsnNode(InsnNode.FCONST, 0f))
            }

            FCONST_1 -> {
                myInsns.add(FloatInsnNode(InsnNode.FCONST, 1f))
            }
            FCONST_2 -> {
                myInsns.add(FloatInsnNode(InsnNode.FCONST, 2f))
            }

            DCONST_0 -> {
                myInsns.add(DoubleInsnNode(InsnNode.DCONST, 0.0))
            }

            DCONST_1 -> {
                myInsns.add(DoubleInsnNode(InsnNode.DCONST, 1.0))
            }

            IADD, LADD, FADD, DADD -> {
                myInsns.add(OpInsnNode(InsnNode.ADD))
            }

            ISUB, LSUB, FSUB, DSUB -> {
                myInsns.add(OpInsnNode(InsnNode.SUB))
            }

            IMUL, LMUL, FMUL, DMUL -> {
                myInsns.add(OpInsnNode(InsnNode.MUL))
            }
            IDIV, LDIV, FDIV, DDIV -> {
                myInsns.add(OpInsnNode(InsnNode.DIV))
            }

            IREM, LREM, FREM, DREM -> {
                myInsns.add(OpInsnNode(InsnNode.REM))
            }

            INEG, LNEG, FNEG, DNEG -> {
                myInsns.add(OpInsnNode(InsnNode.NEG))
            }
            ISHL, LSHL -> {
                myInsns.add(OpInsnNode(InsnNode.SHL))
            }
            ISHR, LSHR -> {
                myInsns.add(OpInsnNode(InsnNode.SHR))
            }
            IUSHR, LUSHR -> {
                myInsns.add(OpInsnNode(InsnNode.USHR))
            }
            IAND, LAND -> {
                myInsns.add(OpInsnNode(InsnNode.AND))
            }
            IOR, LOR -> {
                myInsns.add(OpInsnNode(InsnNode.OR))
            }
            IXOR, LXOR -> {
                myInsns.add(OpInsnNode(InsnNode.XOR))
            }
            IRETURN, LRETURN, FRETURN,
            DRETURN, ARETURN -> {
                myInsns.add(OpInsnNode(InsnNode.ARETURN))
            }

            SWAP -> {
                myInsns.add(OpInsnNode(InsnNode.SWAP))
            }

            RETURN -> {
                myInsns.add(OpInsnNode(InsnNode.RETURN))
            }
        }
    }

    override fun visitLdcInsn(cst: Any?) {
        myInsns.add(LdcInsnNode(InsnNode.PUSH, cst))
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        when (opcode) {
            BIPUSH, SIPUSH -> myInsns.add(IntInsnNode(InsnNode.ICONST, operand))
        // TODO NEWARRAY
        }
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
//        super.visitTypeInsn(opcode, type)
        // TODO CASTS
    }

    override fun visitIincInsn(`var`: Int, increment: Int) {
        super.visitIincInsn(`var`, increment)
    }

}
