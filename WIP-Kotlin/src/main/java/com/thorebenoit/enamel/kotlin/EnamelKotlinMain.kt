package com.thorebenoit.enamel.kotlin

import com.thorebenoit.enamel.kotlin.network.startFileServer
import java.io.File

object EnamelKotlinMain {
    @JvmStatic
    fun main(args: Array<String>) {
       File("/Users/benoit/Documents/").startFileServer()
    }

}
