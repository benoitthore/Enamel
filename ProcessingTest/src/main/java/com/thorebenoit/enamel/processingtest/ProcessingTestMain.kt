package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.print
import com.thorebenoit.enamel.kotlin.threading.coroutine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import processing.core.PApplet
import java.lang.Thread.sleep


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(MainApplet::class.java)
    }

}

class MainApplet : PApplet() {

}