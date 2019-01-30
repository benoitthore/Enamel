package com.thorebenoit.enamel.kotlin.geometry

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint


fun EPoint.toCircle(radius: Number, buffer: ECircle = ECircle()): ECircle {
    buffer.center.set(this)
    buffer.radius = radius.f
    return buffer
}


fun List<EPoint>.toCircles(radius: Number, buffer: List<ECircle> = MutableList(size) { ECircle() }): List<ECircle> {
    forEachIndexed { i, p ->
        p.toCircle(radius, buffer[i])
    }
    return buffer
}

fun ESizeImmutable.toRect(buffer: ERect) = buffer.set(0, 0, width, height)


