package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.kotlin.core.color.colorHSL
import com.thorebenoit.enamel.geometry.AllocationTracker
import com.thorebenoit.enamel.geometry.figures.ECircleMutable
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.innerCircle
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.degrees
import com.thorebenoit.enamel.geometry.toCircles
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet

class RotatingCircle : KotlinPApplet() {
    init {
        AllocationTracker.debugAllocations = false
    }

    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun settings() {
        esize = 800 size 800

    }

    private val circle = ECircleMutable()
    private val points = MutableList(10) { EPointMutable() }
    private val circles = points.toCircles(0f)
    private var startAt = 0.degrees()
    private var increment = 1.degrees()

    override fun draw() {

        background(255)
        fill(0f)

        val circleList = eframe.innerCircle(circle)
            .inset(100)
            .draw()
            .toListOfPoint(points, startAt)
            .toCircles(circle.radius * 0.25f, circles)

        circleList.forEachIndexed { i, it ->
            val color = colorHSL(i.f / circleList.size)
            fill(color)
            it.draw()
        }



        startAt += increment
    }
}