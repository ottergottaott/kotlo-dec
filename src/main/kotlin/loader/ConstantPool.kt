package loader

import java.io.DataInputStream
import java.util.*

class ConstantPool {

//        private var values = arrayOf<Entry>()
    private val values = LinkedList<Entry>()

    fun load(data: DataInputStream) {
        val DEBUG = true
        val entryCount = data.readUnsignedShort()
        var i = 0
        while (i < entryCount - 1) {
            val tag = data.readUnsignedByte()
            val entryType = EntryType.values()[tag]

            when (entryType) {
                EntryType.UTF8 -> {
                    var len = data.readUnsignedShort()
                    val bytes = ByteArray(len)
                    var offs = 0
                    while (len > 0) {
                        val read = data.read(bytes, offs, len)
                        len -= read
                        offs += read
                    }

                    val utf8Entry = Utf8Entry(String(bytes, Charsets.UTF_8))
                    values.add(utf8Entry)

                    if (DEBUG) {
                        println("$i : Utf8 ${utf8Entry.value}")
                    }
                }

                EntryType.INTEGER -> {
                    val intEntry = IntEntry(data.readInt())
                    values.add(intEntry)
                    if (DEBUG) {
                        println("$i : INTEGER ${intEntry.value}")
                    }
                }

                EntryType.FLOAT -> {
                    val floatEntry = FloatEntry(data.readFloat())
                    values.add(floatEntry)
                    if (DEBUG) {
                        println("$i : FLOAT ${floatEntry.value}")
                    }
                }

                EntryType.LONG -> {
                    var value = (data.readInt() as Long) shl 32
                    value = value or (data.readInt() as Long)
                    val longEntry = LongEntry(value)
                    values.add(longEntry)
                    if (DEBUG) {
                        println("$i : LONG ${longEntry.value}")
                    }
                }
                EntryType.DOUBLE -> {
                    var value = (data.readInt() as Long) shl 32
                    value = value or (data.readInt() as Long)
                    val doubleEntry = DoubleEntry(Double.fromBits(value))
                    values.add(doubleEntry)

                    if (DEBUG) {
                        println("$i : DOUBLE ${doubleEntry.value}")
                    }

                }

                EntryType.CLASS -> {
                    val classEntry = ClassEntry(data.readUnsignedShort())
                    values.add(classEntry)

                    if (DEBUG) {
                        println("$i : CLASS ${classEntry.name_index}")

                    }
                }

                EntryType.STRING -> {
                    val stringEntry = StringEntry(data.readUnsignedShort())
                    values.add(stringEntry)
                    if (DEBUG) {
                        println("$i : String ${stringEntry.value_index}")
                    }
                }

                EntryType.FIELD_REF -> {
                    val classIndex = data.readUnsignedShort()
                    val nameAndTypeIndex = data.readUnsignedShort()
                    val fieldRefEntry = FieldRefEntry(classIndex, nameAndTypeIndex)
                    values.add(fieldRefEntry)
                    if (DEBUG) {
                        println("$i : FIELD_REF ${fieldRefEntry.class_index} " +
                                "${fieldRefEntry.name_and_type_index}")
                    }
                }

                EntryType.METHOD_REF -> {
                    val classIndex = data.readUnsignedShort()
                    val nameAndTypeIndex = data.readUnsignedShort()
                    val methodRefEntry = MethodRefEntry(classIndex, nameAndTypeIndex)
                    values.add(methodRefEntry)
                    if (DEBUG) {
                        println("$i : METHOD_REF ${methodRefEntry
                                .class_index} " +
                                "${methodRefEntry.name_and_type_index}")
                    }
                }
                EntryType.INTERFACE_METHOD_REF -> {
                    val classIndex = data.readUnsignedShort()
                    val nameAndTypeIndex = data.readUnsignedShort()
                    val methodRefEntry = MethodRefEntry(classIndex, nameAndTypeIndex)
                    values.add(methodRefEntry)
                    if (DEBUG) {
                        println("$i : INTERFACE_METHOD_RED " +
                                "${methodRefEntry.class_index} " +
                                "${methodRefEntry.name_and_type_index}")
                    }
                }
                EntryType.NAME_AND_TYPE -> {
                    val nameIndex = data.readUnsignedShort()
                    val typeIndex = data.readUnsignedShort()
                    val nameAndTypeEntry = NameAndTypeEntry(nameIndex, typeIndex)
                    values.add(nameAndTypeEntry)
                    if (DEBUG) {
                        println("$i : NAME_AND_TYPE " +
                                "${nameAndTypeEntry.name_index} " +
                                "${nameAndTypeEntry.type_index}")
                    }
                }
                EntryType.METHOD_HANDLE -> {
                    val kind = data.readByte()
                    val referenceIndex = data.readUnsignedShort()
                    val methodHandleEntry = MethodHandleEntry(kind, referenceIndex)
                    values.add(methodHandleEntry)
                    if (DEBUG) {
                        println("$i : METHOD_HANDLE " +
                                "${methodHandleEntry.kind} " +
                                "${methodHandleEntry.reference_index}")
                    }

                }
                EntryType.METHOD_TYPE -> {
                    val descIndex = data.readUnsignedShort()
                    val methodTypeEntry = MethodTypeEntry(descIndex)
                    values.add(methodTypeEntry)
                    if (DEBUG) {
                        println("$i : METHOD_TYPE ${methodTypeEntry.desc_index}")
                    }
                }
                EntryType.INVOKE_DYNAMIC -> {
                    val bootstrapIndex = data.readUnsignedShort()
                    val nameAndTypeIndex = data.readUnsignedShort()
                    val invokeDynamicEntry = InvokeDynamicEntry(bootstrapIndex, nameAndTypeIndex)
                    values.add(invokeDynamicEntry)
                    if (DEBUG) {
                        println("$i : INVOKE_DYNAMIC " +
                                "${invokeDynamicEntry.bootstrap_index} " +
                                "${invokeDynamicEntry.name_and_type_index}")
                    }
                }
                else -> {
                    // TODO mb exception
                    println("Illegal tag in constant pool")
                }
            }
            this.values[i].type = entryType
            if (entryType == EntryType.LONG || entryType == EntryType.DOUBLE) {
                i++
            }
            i++
        }

        i = 0
        while (i < entryCount - 1) {
            val entry = values[i]
            when (entry.type) {
                EntryType.LONG,
                EntryType.DOUBLE -> {
                    i++
                }

                EntryType.CLASS -> {
                    val classEntry = entry as ClassEntry
                    classEntry.name = getUtf8(classEntry.name_index)
                }
                EntryType.STRING -> {
                    val stringEntry = entry as StringEntry
                    stringEntry.value = getUtf8(stringEntry.value_index)
                }
                EntryType.FIELD_REF -> {
                    val fieldRefEntry = entry as FieldRefEntry
                    fieldRefEntry.cls = getUtf8(getClass(fieldRefEntry.class_index).name_index)

                    val nameAndType = getNameAndType(fieldRefEntry.name_and_type_index)
                    fieldRefEntry.name = getUtf8(nameAndType.name_index)
                    fieldRefEntry.type_name = getUtf8(nameAndType.type_index)
                }

                EntryType.METHOD_REF,
                EntryType.INTERFACE_METHOD_REF -> {
                    val methodRefEntry = entry as MethodRefEntry
                    methodRefEntry.cls = getUtf8(getClass(methodRefEntry.class_index).name_index)

                    val nameAndType = getNameAndType(methodRefEntry.name_and_type_index)
                    methodRefEntry.name = getUtf8(nameAndType.name_index)
                    methodRefEntry.type_name = getUtf8(nameAndType.type_index)
                }
                EntryType.NAME_AND_TYPE -> {
                    val nameAndTypeEntry = entry as NameAndTypeEntry
                    nameAndTypeEntry.name = getUtf8(nameAndTypeEntry.name_index)
                    nameAndTypeEntry.type_name = getUtf8(nameAndTypeEntry.type_index)
                }

                EntryType.INVOKE_DYNAMIC -> {
                    val invokeDynamicEntry = entry as InvokeDynamicEntry

                    val nameAndType = getNameAndType(invokeDynamicEntry.name_and_type_index)
                    invokeDynamicEntry.name = getUtf8(nameAndType.name_index)
                    invokeDynamicEntry.type_name = getUtf8(nameAndType.type_index)
                }

                EntryType.METHOD_TYPE -> {
                    val methodTypeEntry = entry as MethodTypeEntry
                    methodTypeEntry.desc = getUtf8(methodTypeEntry.desc_index)
                }

                EntryType.UTF8,
                EntryType.FLOAT,
                EntryType.INTEGER,
                EntryType.METHOD_HANDLE -> {}

                else -> {
                    // TODO mb exception
                }
            }
            i++
        }

    }

    abstract class Entry {
        lateinit var type: EntryType
    }


    class Utf8Entry(var value: String) : Entry()

    class IntEntry(var value: Int) : Entry()

    class FloatEntry(var value: Float) : Entry()

    class LongEntry(var value: Long) : Entry()

    class DoubleEntry(var value: Double) : Entry()

    class StringEntry(var value_index: Int, var value: String = "") : Entry()

    class ClassEntry(var name_index: Int, var name: String = "") : Entry()

    class NameAndTypeEntry(var name_index: Int, var type_index: Int,
                           var name: String = "", var type_name: String = "") : Entry()

    class FieldRefEntry(var class_index: Int, var name_and_type_index: Int,
                        var cls: String = "",
                        var name: String = "", var type_name: String = "") :
            Entry()
    /*{
//        lateinit var cls: String
//        lateinit var name: String // TODO ???
//        lateinit var type_name: String
    }*/

    class MethodRefEntry(var class_index: Int, var name_and_type_index: Int,
                         var cls: String = "", var name: String = "",
                         var type_name: String = "") : Entry()

    class MethodHandleEntry(var kind: Byte, var reference_index: Int) : Entry()

    class MethodTypeEntry(var desc_index: Int, var desc: String = "") : Entry()
    class InvokeDynamicEntry(var bootstrap_index: Int,
                             var name_and_type_index: Int,
                             var name: String = "", var type_name: String = "") :
            Entry()

    enum class EntryType {
        _0,
        UTF8,
        _2,
        INTEGER,
        FLOAT,
        LONG,
        DOUBLE,
        CLASS,
        STRING,
        FIELD_REF,
        METHOD_REF,
        INTERFACE_METHOD_REF,
        NAME_AND_TYPE,
        _13,
        _14,
        METHOD_HANDLE,
        METHOD_TYPE,
        _17,
        INVOKE_DYNAMIC
    }

    fun getEntry(index: Int): Entry = values[index - 1]
    fun getUtf8(index: Int): String = (values[index - 1] as Utf8Entry).value
    fun getInt(index: Int): Int = (values[index - 1] as IntEntry).value
    fun getDouble(index: Int): Double = (values[index - 1] as DoubleEntry).value
    fun getFloat(index: Int): Float = (values[index - 1] as FloatEntry).value
    fun getLong(index: Int): Long = (values[index - 1] as LongEntry).value
    fun getClass(index: Int): ClassEntry = values[index - 1] as ClassEntry
    fun getFieldRef(index: Int): FieldRefEntry = values[index - 1] as FieldRefEntry
    fun getMethodRef(index: Int): MethodRefEntry = values[index - 1] as MethodRefEntry
    fun getNameAndType(index: Int): NameAndTypeEntry = values[index - 1] as NameAndTypeEntry
    fun getInterfaceMethodRef(index: Int): MethodRefEntry = values[index - 1] as MethodRefEntry
    fun getMethodHandle(index: Int): MethodHandleEntry = values[index - 1] as MethodHandleEntry
    fun getInvokeDynamic(index: Int): InvokeDynamicEntry = values[index - 1] as InvokeDynamicEntry


}