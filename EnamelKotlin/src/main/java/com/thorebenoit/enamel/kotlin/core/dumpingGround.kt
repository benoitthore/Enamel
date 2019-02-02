package com.thorebenoit.enamel.kotlin.core

import java.io.File
import java.lang.Exception
import java.lang.management.ManagementFactory
import java.nio.charset.Charset

fun executeHeapDump(destinationFolder: String = "/tmp/", fileName: String = "heapdump.hprof"): String {
    val pid = ManagementFactory.getRuntimeMXBean().name.split("@")[0]

    val destinationFile = File(destinationFolder,fileName).absolutePath
    val command = "jmap -dump:format=b,file=$destinationFile $pid"

    return command.split(" ").executeInTerminal()
}

fun String.executeInTerminal() =
    Runtime.getRuntime().exec(this)
        .inputStream.readBytes().toString(Charset.defaultCharset())

fun List<String>.executeInTerminal() =
    Runtime.getRuntime().exec(this.toTypedArray())
        .inputStream.readBytes().toString(Charset.defaultCharset())

val <T> T.print get() = apply { println(this) }

fun tryCatch(tryBlock: () -> Unit, catchBlock: () -> Unit = {}) {
    try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock()
    }
}