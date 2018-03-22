package ast.signature

/**
 * A type ast.signature which can be either a type variable or a concrete class or
 * void.
 */
abstract class TypeSignature {

    abstract val name: String

    abstract val descriptor: String

    val isArray: Boolean
        get() = false

    /**
     * Gets if this ast.signature has any type arguments.
     */
    abstract fun hasArguments(): Boolean
//
//    @Throws(IOException::class)
//    abstract fun writeTo(pack: MessagePacker)

//    companion object {
//
//        /**
//         * Gets a [TypeSignature] which is an array of the given type.
//         */
//        fun arrayOf(type: TypeSignature): TypeSignature {
//            if (type is GenericClassTypeSignature) {
//                val sig = type as GenericClassTypeSignature
//                val array = GenericClassTypeSignature("[" + sig.getType())
//                array.getArguments().addAll(sig.getArguments())
//                return array
//            } else if (type is ClassTypeSignature) {
//                val sig = type as ClassTypeSignature
//                return ClassTypeSignature.of("[" + sig.getType())
//            } else if (type is TypeVariableSignature) {
//                val sig = type as TypeVariableSignature
//                return TypeVariableSignature("[" + sig.getIdentifier())
//            }
//            throw IllegalStateException()
//        }
//
//        /**
//         * Gets the component [TypeSignature] of the given array type.
//         */
//        fun getArrayComponent(type: TypeSignature): TypeSignature {
//            if (type is GenericClassTypeSignature) {
//                val sig = type as GenericClassTypeSignature
//                val array = GenericClassTypeSignature(sig.getType().substring(1))
//                array.getArguments().addAll(sig.getArguments())
//                return array
//            } else if (type is ClassTypeSignature) {
//                val sig = type as ClassTypeSignature
//                return ClassTypeSignature.of(sig.getType().substring(1), true)
//            } else if (type is TypeVariableSignature) {
//                val sig = type as TypeVariableSignature
//                return TypeVariableSignature(sig.getIdentifier().substring(1))
//            }
//            throw IllegalStateException()
//        }
//    }

}
