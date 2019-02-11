package com.thorebenoit.enamel.kotlin.geometry

import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import kotlin.math.pow


fun EPointType.toCircle(radius: Number, buffer: ECircle = ECircle()): ECircle {
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

fun ESizeImmutable.toRect(buffer: ERect = ERect()) = buffer.set(0, 0, width, height)


fun ERectType.innerCircle(buffer: ECircle = ECircle()): ECircle {
    center(buffer.center) // set circles center to rect center
    buffer.radius = size.min / 2
    return buffer
}


fun ERectType.outterCircle(buffer: ECircle = ECircle()): ECircle {
    center(buffer.center) // set circles center to rect center
    buffer.radius = size.diagonal / 2
    return buffer
}

fun ECircleImmutable.outterRect(buffer: ERect = ERect()): ERect {
    val width = radius * 2
    buffer.width = width
    buffer.height = width
    buffer.center = center
    return buffer
}

fun ECircleImmutable.innerRect(buffer: ERect = ERect()): ERect {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    buffer.width = width
    buffer.height = width
    buffer.center = center
    return buffer
}