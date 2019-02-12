package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.executeInTerminal
import com.thorebenoit.enamel.kotlin.core.tryCatch
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletModule
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.*
import processing.core.PApplet
import processing.core.PConstants
import java.lang.Exception


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

    var temperature: String = "Loading"

    init {

        coroutine {
            while (true) {
                try {
                    temperature = "istats cpu temp".executeInTerminal().removeDoubleSpaces().split(" ")[2]
                    temperature = temperature.substring(0, temperature.indexOf('C') + 1)
                    delay(5_000)
                    loop()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        onSettings {
            esize = 150 size 150
        }

        // TODO transparentWindow is required for drawing to happen
        alwaysOnTop()
        transparentWindow()
        draggableWindow()


        val border = 2

        onDraw {

            fill(0)
            eframe.innerCircle().draw()

            fill(255)
            eframe.innerCircle().inset(border).draw()

            fill(255f, 0f, 0f)
//            val w = textWidth(temperature)
//            text(temperature, center.x - w / 2, center.y)
            textAlign(PConstants.CENTER, PConstants.CENTER)
            text(temperature, center.x, center.y)

            noLoop()
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


