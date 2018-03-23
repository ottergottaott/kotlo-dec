package ast.type

import ast.SourceSet
import com.google.common.collect.Lists
import java.io.IOException

/**
 * Represents an enum type.
 */
class EnumEntry(src: SourceSet, name: String) : TypeEntry(src, name) {

    protected val enum_constants: MutableList<String> = Lists.newArrayList()

    /**
     * Gets a list of enum constants present in this enum.
     */
    val enumConstants: List<String>
        get() = this.enum_constants

    /**
     * Adds an enum constant to this enum.
     */
    fun addEnumConstant(cst: String) {
        this.enum_constants.add(checkNotNull(cst))
    }

    override fun toString() = "Enum $name"


}
