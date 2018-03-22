package ast

import decomiler.Decompiler

/**
 * Represents the access modifier of a class member.
 */
enum class AccessModifier constructor(private val ident: String) {

    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_PRIVATE(""),
    PRIVATE("private");

    /**
     * Gets the string representation of this access modifier.
     */
    fun asString(): String {
        return this.ident
    }

    companion object {

        /**
         * Gets the access modifier from the given flags. See
         * [Opcodes.ACC_PUBLIC], [Opcodes.ACC_PROTECTED],
         * [Opcodes.ACC_PRIVATE]. A flag with none of these set will be marked
         * as [.PACKAGE_PRIVATE].
         */
        fun fromModifiers(access: Int): AccessModifier {
            if (access and Decompiler.ACC_PUBLIC != 0) {
                return PUBLIC
            } else if (access and Decompiler.ACC_PROTECTED != 0) {
                return PROTECTED
            } else if (access and Decompiler.ACC_PRIVATE != 0) {
                return PRIVATE
            }
            return PACKAGE_PRIVATE
        }
    }

}
