package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.i
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.core.tryCatch
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import processing.core.PApplet


abstract class KotlinPApplet : PApplet() {

    val eframe get() = ERect(size = esize)
    var esize: ESize
        get() = width size height
        set(value) {
            tryCatch({
                size(value.width.i, value.height.i)
            }) {
                coroutine {
                    kotlinx.coroutines.delay(10)
                    esize = value
                }
            }
        }
    val ecenter get() = eframe.center(EPoint())

    fun ECircle.draw(): ECircle {
        ellipse(x, y, radius * 2, radius * 2)
        return this
    }

    fun ERect.draw(): ERect {
        rect(x, y, width, height)
        return this
    }

    override fun settings() {
        size(400, 400)
    }
}