package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.geometry.AllocationTracker
import com.thorebenoit.enamel.geometry.figures.ECircle
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.innerCircle
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.degrees
import com.thorebenoit.enamel.geometry.toCircle
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet

class RadarApplet : KotlinPApplet() {

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

    var step = 0

    data class Beep(val position: EPointMutable, var progress: Float = 0f)

    val beepArray = mutableListOf<Beep>()
    override fun draw() {
        background(0)

        noFill()
        stroke(0f, 255f, 0f)

        val circle = eframe.innerCircle(ECircle()).inset(25)
        circle.draw()

        val start = circle.center

        val angle = (-90 + step).degrees()

        val end = start.copy().offsetAngle(angle, circle.radius)
        line(start.x, start.y, end.x, end.y)


        if (Math.random() < .05) {
            val atRadius = com.thorebenoit.enamel.core.math.random(0.25, 0.80) * circle.radius
            val beep = Beep(circle.center.copy().offsetAngle(angle, atRadius))
            beepArray += beep
        }

        beepArray.forEach {

            fill(0f, 255f, 0f)
            it.position.toCircle(4f * it.progress, ECircle()).draw()
            it.progress += 1 / 360f
        }

        beepArray.removeIf { it.progress > 1f }

        step++
    }


}
