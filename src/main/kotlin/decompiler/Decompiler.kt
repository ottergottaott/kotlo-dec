package decomiler


import ast.AccessModifier
import ast.signature.ClassTypeSignature
import loader.ConstantPool
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream


class Decompiler {

    fun decompile(input: InputStream, set: SourceSet) {
        /*TODO: Actually should not be here*/
        val data = if (input is DataInputStream) input else DataInputStream(input)


        val magic = data.readInt()
        if (magic != -0x35014542) {
            throw IllegalArgumentException("Not a java class file")
            // TODO: write own exception
        }

        data.readShort()
        data.readShort()
        /* END */

        val constantPool = ConstantPool()
        constantPool.load(data)

        val access_flags = data.readUnsignedShort()

        val name = constantPool.getClass(data.readUnsignedShort()).name
//        if (!LibraryConfiguration.quiet) {
        println("Decompiling class $name")
//        }

        val super_index = data.readUnsignedShort()
        val supername = if (super_index != 0) "L ${constantPool.getClass(super_index).name};" else "Ljava/lang/Object;"

        val interfaces_count = data.readUnsignedShort()
        val interfaces = ArrayList<String>(interfaces_count)
        for (i in 0 until interfaces_count) {
            interfaces.add(constantPool.getClass(data.readUnsignedShort()).name)
        }


        // TODO work with access_flags
        var entry: TypeEntry? = null
//        if (access_flags and ACC_ANNOTATION != 0) {
//            entry = AnnotationEntry(set, actual_lang, name)
//        } else if (access_flags and ACC_INTERFACE != 0) {
//            entry = InterfaceEntry(set, actual_lang, name)
//        } else if (access_flags and ACC_ENUM != 0) {
//            entry = EnumEntry(set, actual_lang, name)
//        } else {
//            entry = ClassEntry(set, actual_lang, name)
//            (entry as ClassEntry).setSuperclass(supername)
//        }
//        entry!!.setAccessModifier(AccessModifier.fromModifiers(access_flags))
//        entry!!.setFinal(access_flags and ACC_FINAL != 0)
//        entry!!.setSynthetic(access_flags and ACC_SYNTHETIC != 0)
//        entry!!.setAbstract(access_flags and ACC_ABSTRACT != 0)
//        entry!!.getInterfaces().addAll(interfaces)

        /* Collect fields */
        val field_count = data.readUnsignedShort()
        for (i in 0 until field_count) {
            val field_access = data.readUnsignedShort()
            val field_name = constantPool.getUtf8(data.readUnsignedShort())
            if (field_access and ACC_ENUM != 0) {
                (entry as EnumEntry).addEnumConstant(field_name)
            }
            val field_desc = constantPool.getUtf8(data.readUnsignedShort())

            val field = FieldEntry(set)
            field.setAccessModifier(AccessModifier.fromModifiers(field_access))
            field.setFinal(field_access and ACC_FINAL != 0)
            field.setName(field_name)
            field.setOwner(name)
            field.setStatic(field_access and ACC_STATIC != 0)
            field.setSynthetic(field_access and ACC_SYNTHETIC != 0)
            field.setVolatile(field_access and ACC_VOLATILE != 0)
            field.setTransient(field_access and ACC_TRANSIENT != 0)
            field.setName(field_name)
            field.setType(ClassTypeSignature.of(field_desc))
            entry.addField(field)

            val attribute_count = data.readUnsignedShort()
            for (a in 0 until attribute_count) {
                val attribute_name = constantPool.getUtf8(data.readUnsignedShort())
                val length = data.readInt()
                if ("ConstantValue" == attribute_name) {
                    /* int constant_value_index = */ data.readUnsignedShort()
                } else if ("Synthetic" == attribute_name) {
                    field.setSynthetic(true)
                } else if ("Signature" == attribute_name) {
                    field.setType(SignatureParser.parseFieldTypeSignature(constantPool.getUtf8(data.readUnsignedShort())))
                } else if ("Deprecated" == attribute_name) {
                    field.setDeprecated(true)
                } else if ("RuntimeVisibleAnnotations" == attribute_name) {
                    val annotation_count = data.readUnsignedShort()
                    for (j in 0 until annotation_count) {
                        val anno = readAnnotation(data, constantPool, set)
                        field.addAnnotation(anno)
                        anno.getType().setRuntimeVisible(true)
                    }
                } else if ("RuntimeInvisibleAnnotations" == attribute_name) {
                    val annotation_count = data.readUnsignedShort()
                    for (j in 0 until annotation_count) {
                        val anno = readAnnotation(data, constantPool, set)
                        field.addAnnotation(anno)
                        anno.getType().setRuntimeVisible(false)
                    }
                } else {
                    System.err.println("Skipping unknown field attribute: $attribute_name")
                    data.skipBytes(length)
                }
            }
        }

        println("tested")
    }

    private fun readAnnotation(data: DataInputStream, pool: ConstantPool, set: SourceSet): Annotation {
        val anno_type_name = pool.getUtf8(data.readUnsignedShort())
        val anno_type = set.getAnnotationType(TypeHelper.descToType(anno_type_name))
        val anno = Annotation(anno_type)
        val value_paris = data.readUnsignedShort()
        for (k in 0 until value_paris) {
            val element_name = pool.getUtf8(data.readUnsignedShort())
            anno.setValue(element_name, readElementValue(data, pool, set))
        }
        return anno
    }

    @Throws(IOException::class)
    private fun readElementValue(data: DataInputStream, pool: ConstantPool, set: SourceSet): Any {
        val element_type_tag = data.readUnsignedByte().toChar()


        when (element_type_tag) {
            's' -> return pool.getUtf8(data.readUnsignedShort())
            'B' -> {
                val value = pool.getInt(data.readUnsignedShort())
                return java.lang.Byte.valueOf(value.toByte())
            }
            'S' -> {
                val value = pool.getInt(data.readUnsignedShort())
                return java.lang.Short.valueOf(value.toShort())
            }
            'C' -> {
                val value = pool.getInt(data.readUnsignedShort())
                return Character.valueOf(value.toChar())
            }
            'I' -> {
                val value = pool.getInt(data.readUnsignedShort())
                return Integer.valueOf(value)
            }
            'F' -> {
                val value = pool.getFloat(data.readUnsignedShort())
                return java.lang.Float.valueOf(value)
            }
            'J' -> {
                val value = pool.getLong(data.readUnsignedShort())
                return java.lang.Long.valueOf(value)
            }
            'D' -> {
                val value = pool.getDouble(data.readUnsignedShort())
                return java.lang.Double.valueOf(value)
            }
            'Z' -> {
                val value = pool.getInt(data.readUnsignedShort())
                return java.lang.Boolean.valueOf(value != 0)
            }
            'c' -> {
                val value = pool.getUtf8(data.readUnsignedShort())
                return ClassTypeSignature.of(value)
            }
            '@' -> return readAnnotation(data, pool, set)
            'e' -> {
                val enum_type = pool.getUtf8(data.readUnsignedShort())
                val enum_cst = pool.getUtf8(data.readUnsignedShort())
                return EnumConstant(enum_type, enum_cst)
            }
            '[' -> {
                val value = java.util.ArrayList<Any>()
                val num_values = data.readUnsignedShort()
                for (i in 0 until num_values) {
                    value.add(readElementValue(data, pool, set))
                }
                return value
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    companion object {
        val ACC_PUBLIC = 0x0001
        val ACC_PRIVATE = 0x0002
        val ACC_PROTECTED = 0x0004
        val ACC_STATIC = 0x0008
        val ACC_FINAL = 0x0010
        val ACC_SUPER = 0x0020
        val ACC_SYNCHRONIZED = 0x0020
        val ACC_VOLATILE = 0x0040
        val ACC_BRIDGE = 0x0040
        val ACC_TRANSIENT = 0x0080
        val ACC_VARARGS = 0x0080
        val ACC_NATIVE = 0x0100
        val ACC_INTERFACE = 0x0200
        val ACC_ABSTRACT = 0x0400
        val ACC_STRICT = 0x0800
        val ACC_SYNTHETIC = 0x1000
        val ACC_ANNOTATION = 0x2000
        val ACC_ENUM = 0x4000
    }


}