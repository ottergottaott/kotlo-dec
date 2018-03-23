package ast.type

import ast.SourceSet
import java.io.IOException

/**
 * Represents an interface type.
 */
class AnnotationEntry(src: SourceSet, name: String) : TypeEntry(src, name) {

    override fun toString(): String = "Interface ${this.name}"


//    fun accept(visitor: AstVisitor) {
//        if (visitor is TypeVisitor) {
//            (visitor as TypeVisitor).visitAnnotationEntry(this)
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
