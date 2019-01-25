package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.ImmutableERect
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import processing.core.PApplet


abstract class KotlinPApplet : PApplet() {

    val eframe get() = ImmutableERect(size = esize)
    val esize get() = width size height

    fun ECircle.draw(): ECircle {
        ellipse(center.x, center.y, radius, radius)
        return this
    }

    override fun settings() {
        size(400, 400)
    }
}