package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.innerCircle
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
        size(200,200)
    }
    override fun draw() {
        fill(255f, 0f, 0f)

//        ellipse(100f,100f,100f,100f)
        eframe.innerCircle(circle)
            .insetBy(10)
            .draw()
//            .pointsInList(points, startAt)
//            .toCircles(circle.radius * 0.8f, circles)
//            .forEach {
//                it.draw()
//            }



        startAt += increment
    }

}