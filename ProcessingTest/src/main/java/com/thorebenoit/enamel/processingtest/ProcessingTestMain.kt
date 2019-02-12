package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletModule
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.*
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {

        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false


        PApplet.main(MainApplet::class.java)
//        PApplet.main(TestApplet2::class.java)
    }

}


///
class MainApplet : KotlinPAppletModule() {

    init {

        onSettings {
            esize = 100 size 100
        }

        alwaysOnTop()
        transparentWindow()
        draggableWindow()

        onDraw {
            eframe.innerCircle().draw()
        }
    }


}

// TODO Move to String extensions, or near execute in terminal function
private fun String.removeDoubleSpaces(): String {
    val doubleSpace = "  "
    var s = this
    while (s.contains(doubleSpace) || s.contains("\t")) {
        s = replace("\t", " ").replace(doubleSpace, "")
    }
    return s
}


