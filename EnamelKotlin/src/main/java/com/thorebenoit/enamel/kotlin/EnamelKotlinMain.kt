package com.thorebenoit.enamel.kotlin

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.server.startFileServer
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine
import java.io.File

object EnamelKotlinMain {
    @JvmStatic
    fun main(args: Array<String>) {
       File("/Users/benoit/Documents/").startFileServer()
    }

}
