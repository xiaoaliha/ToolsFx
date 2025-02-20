package me.leon

import me.leon.ext.toFile
import org.junit.Test

class FileTest {
    @Test
    fun gradleCacheJarToM2() {
        val home = System.getenv("USERPROFILE")
        println(home)
        val gradleJarsDir = "$home\\.gradle\\caches\\modules-2\\files-2.1\\"
        val m2Dir = "$home\\.m2\\repository"

        gradleJarsDir.toFile().walk().filter { it.isFile }.forEach {
            val path =
                it.absolutePath.replace(gradleJarsDir, "").replace("""\\\w{38,}""".toRegex(), "")
            val properPath =
                path.substringBefore("\\").replace(".", "\\") + "\\" + path.substringAfter("\\")
            val dstFile = (m2Dir + "\\" + properPath).toFile()
            if (dstFile.exists().not()) {
                println("copy: ${dstFile.absolutePath}")
                //                it.copyTo(dstFile)
                if (dstFile.parentFile.exists().not()) {
                    dstFile.parentFile.mkdirs()
                }
                val renameState = it.renameTo(dstFile)
                if (!renameState) {
                    println("rename failed: ${dstFile.absolutePath}")
                    it.copyTo(dstFile)
                }
            } else {
                val delete = it.delete()
                println("exist: $it ")
            }
        }
    }
}
