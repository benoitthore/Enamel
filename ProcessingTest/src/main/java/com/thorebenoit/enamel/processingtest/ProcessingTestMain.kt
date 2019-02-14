package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.limitSizeLast
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.functions.linearRegression
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.genetics.randomWithWeigth
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.div
import com.thorebenoit.enamel.kotlin.geometry.primitives.times
import com.thorebenoit.enamel.processingtest.examples.genetics.GeneticPresenter
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawingApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet._onMouseClicked
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.invertY
import com.thorebenoit.enamel.processingtest.kotlinapplet.toEPoint
import kotlin.math.pow


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

    }

    @JvmStatic
    fun main(args: Array<String>) {


        val applet = KotlinPApplet.createApplet<MainApplet>()
//        val applet = KotlinPApplet.createApplet<DotDrawingApplet>()
//        GeneticPresenter(applet)


    }

}

class MainApplet : KotlinPApplet() {


    val points: MutableList<EPoint> = mutableListOf()

    init {
        _onMouseClicked {
            points += mousePosition.copy()

            loop()
        }
    }


    override fun draw() {

        background(0)

        fill(255)
        stroke(255)
        points.linearRegression().toLine(width).draw().print

        noStroke()
        fill(255)
        points.forEach { (it).draw() }
        points.limitSizeLast(3)

        noLoop()
    }
}


