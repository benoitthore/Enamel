package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.colorHSL
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.core.random
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.geometry.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(MainApplet::class.java)
    }

}

/*
These function are used to help drawing only, they shouldn't be put in the library yet
 */
private operator fun ESizeImmutable.div(n: Number) = ESize(width / n.f, height / n.f)

private operator fun EPointType.div(n: Number) = EPoint(x / n.f, y / n.f)

///
class MainApplet : KotlinPApplet() {


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }


    override fun settings() {
        super.settings()
        AllocationTracker.debugAllocations = false
        esize = 800 size 800
    }


    var angle = 0.degrees()
    val deltaTimer = EDeltaTimer()
    override fun draw() {

        background(255)

        stroke(colorHSL(0f))
        strokeWeight(2f)

        noFill()


        deltaTimer.frame { delta->

            angle.offset((delta).degrees())

            val circle = eframe.innerCircle().inset(width * 0.1)

            circle
                .toListOfPoint(10, angle)
                .map { it.toCircle(width * 0.05) }
                .forEach {
                    it
//                    .draw()
                        .innerRect()
                        .draw()
                }

        }
//        Thread.sleep(100)
    }


}

