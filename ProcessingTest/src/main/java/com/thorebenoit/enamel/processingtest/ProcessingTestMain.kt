package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.kotlin.print
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine
import processing.core.PApplet
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer


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

private operator fun EPointImmutable.div(n: Number) = EPoint(x / n.f, y / n.f)
///
class MainApplet : KotlinPApplet() {


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun settings() {
        esize = 800 size 800
    }


    override fun draw() {
        background(0)
//        stroke(colorHSL(0f))
//        strokeWeight(2f)
//
//        noFill()
//
//        val rect = eframe
//        val buffer = ERect()
//
//        rect.inset(eframe.size.min * 0.1f)
//            .draw()
//            .rectAlignedOutside(
//                aligned = EAlignment.bottomCenter,
//                size = rect.size.copy().scale(0.5f)
////                    .apply {
////                        this.width = min
////                        this.height = min
////                    }
//                , buffer = buffer
//            ).draw()


    }


}

