package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.core.colorHSL
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.kotlin.geometry.toCircles
import com.thorebenoit.enamel.processingtest.KotlinPApplet

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

    private val circle = ECircle()
    private val points = MutableList(10) { EPoint() }
    private val circles = points.toCircles(0f)
    private var startAt = 0.degrees()
    private var increment = 1.degrees()

    override fun draw() {

        background(255)
        fill(0f)

        val circleList = eframe.innerCircle(circle)
            .inset(100)
            .draw()
            .pointsInList(points, startAt)
            .toCircles(circle.radius * 0.25f, circles)

        circleList.forEachIndexed { i, it ->
            val color = colorHSL(i.f / circleList.size)
            fill(color)
            it.draw()
        }



        startAt += increment
    }
}