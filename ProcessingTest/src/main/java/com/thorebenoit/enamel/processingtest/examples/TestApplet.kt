package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.KotlinPApplet
import processing.core.PApplet

class TestApplet : KotlinPApplet() {


    init {
        AllocationTracker.debugAllocations = false
    }

    private lateinit var circle: ECircle
    override fun setup() {
        circle = eframe.inset(esize.min * 0.90).innerCircle()

        onMouseClicked {
            (i++).print
            loop()
        }
    }

    var i = 3;

    override fun draw() {

        fill(255f,0f,0f)
        val p = circle.toListOfPoint(i).forEach {
            it.toCircle(3).draw()
        }

//        line(center.x, center.y, p.x, p.y)

        noLoop()
    }
}