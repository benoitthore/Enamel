package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.figures.toCircles
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(MainApplet::class.java)
    }

}

class MainApplet : KotlinPApplet() {

    private val circle = ECircle()
    private val points = MutableList(10) { EPoint() }
    private val circles = points.toCircles(0f)
    private var startAt = 0.degrees()
    private var increment = 1.degrees()


    override fun settings() {
        esize = 800 size 800
    }
    override fun draw() {
        background(255)

        fill(255f, 0f, 0f)

        eframe.innerCircle(circle)
            .insetBy(100)
            .draw()
            .pointsInList(points, startAt)
            .toCircles(circle.radius * 0.25f, circles)
            .forEach {
                it.draw()
            }



        startAt += increment
    }

}