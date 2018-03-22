package ir

import ir.Insn
import java.util.*

class InsnBlock : Iterable<Insn> {

    val instructions: MutableList<Insn>

    var opcodeIndices: IntArray? = null
//    val catchRegions: List<TryCatchRegion> = ArrayList<E>()

    init {
        this.instructions = ArrayList()
    }

    operator fun get(i: Int) = instructions[i]
    fun size() = instructions.size
    fun append(insn: Insn) = instructions.add(insn)


    override fun toString(): String {
        val str = StringBuilder()
        for (insn in this.instructions) {
            str.append(" ").append(insn).append("\n")
        }
        return str.toString()
    }

    override fun iterator(): Iterator<Insn> {
        return Itr()
    }

    inner class Itr : Iterator<Insn> {

        private var index: Int = 0

        override fun hasNext(): Boolean {
            return this@InsnBlock.instructions.size > this.index
        }

        override fun next(): Insn {
            return this@InsnBlock.instructions[this.index++]
        }

    }

}
