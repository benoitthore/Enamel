package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet

class TestApplet : KotlinPApplet() {


    init {
        AllocationTracker.debugAllocations = false
    }

    private lateinit var circle: ECircle
    override fun setup() {
        circle = eframe.innerCircle().inset(100)

        onMouseClicked {
            (i++).print
            loop()
        }
    }

    override fun settings() {
        esize = 1000 size 1000
    }

    var i = 3;

    override fun draw() {
        background(255)
        noFill()
        stroke(0)
        circle.draw()

        circle.center.draw()

        fill(255f, 0f, 0f)


        circle
            .toListOfPoint(i)
            .forEachIndexed { i, it ->
                it.toCircle(3).draw()
                fill(0f)
                text(i.toString(), it.x, it.y)
                fill(255f, 0f, 0f)
                line(center.x, center.y, it.x, it.y)
            }


        noLoop()
    }
}