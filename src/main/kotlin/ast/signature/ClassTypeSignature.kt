package ast.signature

/**
 * A type ast.signature of a class or primative type (but not void).
 */
class ClassTypeSignature(var type_name: String) {

    companion object {
        val BOOLEAN = ClassTypeSignature("Z")
        val BYTE = ClassTypeSignature("B")
        val SHORT = ClassTypeSignature("S")
        val INT = ClassTypeSignature("I")
        val LONG = ClassTypeSignature("J")
        val FLOAT = ClassTypeSignature("F")
        val DOUBLE = ClassTypeSignature("D")
        val CHAR = ClassTypeSignature("C")
        val OBJECT = ClassTypeSignature("Ljava/lang/Object;")
        val STRING = ClassTypeSignature("Ljava/lang/String;")
        val BOOLEAN_OBJECT = ClassTypeSignature("Ljava/lang/Boolean;")
        val BYTE_OBJECT = ClassTypeSignature("Ljava/lang/Byte;")
        val SHORT_OBJECT = ClassTypeSignature("Ljava/lang/Short;")
        val INTEGER_OBJECT = ClassTypeSignature("Ljava/lang/Integer;")
        val LONG_OBJECT = ClassTypeSignature("Ljava/lang/Long;")
        val FLOAT_OBJECT = ClassTypeSignature("Ljava/lang/Float;")
        val DOUBLE_OBJECT = ClassTypeSignature("Ljava/lang/Double;")
        val CHARACTER_OBJECT = ClassTypeSignature("Ljava/lang/Character;")

        val SPECIAL: MutableMap<String, ClassTypeSignature> = HashMap<String, ClassTypeSignature>();

        init {
            SPECIAL.put(BOOLEAN.type_name, BOOLEAN);
            SPECIAL.put(BYTE.type_name, BYTE);
            SPECIAL.put(SHORT.type_name, SHORT);
            SPECIAL.put(INT.type_name, INT);
            SPECIAL.put(LONG.type_name, LONG);
            SPECIAL.put(FLOAT.type_name, FLOAT);
            SPECIAL.put(DOUBLE.type_name, DOUBLE);
            SPECIAL.put(CHAR.type_name, CHAR);

            SPECIAL.put(STRING.type_name, STRING);
            SPECIAL.put(OBJECT.type_name, OBJECT);

            SPECIAL.put(BOOLEAN_OBJECT.type_name, BOOLEAN_OBJECT);
            SPECIAL.put(BYTE_OBJECT.type_name, BYTE_OBJECT);
            SPECIAL.put(SHORT_OBJECT.type_name, SHORT_OBJECT);
            SPECIAL.put(INTEGER_OBJECT.type_name, INTEGER_OBJECT);
            SPECIAL.put(LONG_OBJECT.type_name, LONG_OBJECT);
            SPECIAL.put(FLOAT_OBJECT.type_name, FLOAT_OBJECT);
            SPECIAL.put(DOUBLE_OBJECT.type_name, DOUBLE_OBJECT);
            SPECIAL.put(CHARACTER_OBJECT.type_name, CHARACTER_OBJECT);
        }

        fun of(type: String): ClassTypeSignature = of(type, false);


        fun of(type: String, noSpecial: Boolean): ClassTypeSignature {
            if (!noSpecial) {
                val sig = SPECIAL.get(type)
                if (sig != null) {
                    return sig
                }
            }
            return ClassTypeSignature(type)

        }
    }



}
