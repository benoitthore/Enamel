package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.*
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.processingtest.examples.AppletListApplet
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(AppletListApplet::class.java)
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
        super.settings()
//        esize = 800 size 800
        esize = allocate { 400 size 400 }

    }

    //    val sizesList = List(4) { ESize(random(10, 50), random(10, 50)) }

    override fun draw() {

        background(255)

        stroke(colorHSL(0f))
        strokeWeight(2f)

        noFill()


        //        EAlignment.all.forEach { alignment ->
//
//            frameRect.rectAlignedInside(
//                aligned = alignment,
//                spacing = 20,
//                size = 40 size 40,
//                buffer = buffer
//            )
//
//            buffer.draw()
//        }


    }


}

