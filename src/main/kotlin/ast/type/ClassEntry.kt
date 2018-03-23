package ast.type

import ast.SourceSet
import utils.TypeHelper
import java.io.IOException



/**
 * Represents a class type.
 */
class ClassEntry(src: SourceSet, name: String) : TypeEntry(src, name) {

    /**
     * Gets the super class of this type.
     */
    /**
     * Sets the super class of this entry.
     */
    lateinit var superclass: String

    /**
     * Gets the name of the superclass of this type.
     */
    val superclassName: String
        get() = TypeHelper.descToType(this.superclass)

//    fun accept(visitor: AstVisitor) {
//        if (visitor is TypeVisitor) {
//            (visitor as TypeVisitor).visitClassEntry(this)
//        }
//        for (field in this.fields.values()) {
//            field.accept(visitor)
//        }
//        for (field in this.static_fields.values()) {
//            field.accept(visitor)
//        }
//        for (method in this.methods.values()) {
//            method.accept(visitor)
//        }
//        for (method in this.static_methods.values()) {
//            method.accept(visitor)
//        }
//        for (anno in this.annotations.values()) {
//            anno.accept(visitor)
//        }
//        if (visitor is TypeVisitor) {
//            (visitor as TypeVisitor).visitTypeEnd()
//        }
//    }

}
