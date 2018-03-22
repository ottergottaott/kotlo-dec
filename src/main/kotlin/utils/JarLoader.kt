package utils

import decomiler.Decompiler
import loader.ConstantPool
import java.io.BufferedInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarInputStream

class JarLoader(private val jarPath: Path) {

    fun load() {

        JarInputStream(BufferedInputStream(Files.newInputStream(jarPath))).use {
            while (true) {
                val entry = it.nextJarEntry
                entry ?: break


                val name = entry.name
                if (!name.endsWith(".class")) {
                    continue
                }

                val decompiler = Decompiler()
                decompiler.decompile(it)
                println(entry.name)
            }
        }

    }
}