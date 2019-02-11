package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.processingtest.KotlinPApplet

class BasicCock : KotlinPApplet() {
    init {
        AllocationTracker.debugAllocations = false
    }


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun settings() {
        super.settings()
        esize = 800 size 800

    }

    var step = (-90f) * 60
    var speed = 1.5f
    override fun draw() {
        background(0)

        strokeWeight(2f)

        val circle = eframe.innerCircle(ECircle())

        noStroke()

        circle.draw()

        kotlin.run {
            stroke(255f, 0f, 0f)
            val angle = step.degrees()
            val start = circle.center
            val end = start.offsetAngle(angle, circle.radius * 0.75)
            line(start.x, start.y, end.x, end.y)
        }


        kotlin.run {
            stroke(0f, 0f, 255f)
            val angle = (step / 12).degrees()
            val start = circle.center
            val end = start.offsetAngle(angle, circle.radius * 0.5)
            line(start.x, start.y, end.x, end.y)
        }


        step += ((360f) / 60f) * speed

    }


}
