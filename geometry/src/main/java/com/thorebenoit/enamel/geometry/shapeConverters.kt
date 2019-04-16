package com.thorebenoit.enamel.geometry

import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.geometry.figures.*
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.EPoint


fun EPoint.toCircle(radius: Number, buffer: ECircleMutable = ECircleMutable()): ECircleMutable {
    buffer.center.set(this)
    buffer.radius = radius.f
    return buffer
}


fun List<EPointMutable>.toCircles(radius: Number, buffer: List<ECircleMutable> = MutableList(size) { ECircleMutable() }): List<ECircleMutable> {
    forEachIndexed { i, p ->
        p.toCircle(radius, buffer[i])
    }
    return buffer
}

fun ESize.toRect(buffer: ERect = ERect()) = buffer.set(0, 0, width, height)


fun ERectType.innerCircle(buffer: ECircleMutable = ECircleMutable()): ECircleMutable {
    center(buffer.center) // set circles center to rect center
    buffer.radius = size.min / 2
    return buffer
}


fun ERectType.outterCircle(buffer: ECircleMutable = ECircleMutable()): ECircleMutable {
    center(buffer.center) // set circles center to rect center
    buffer.radius = size.diagonal / 2
    return buffer
}

fun ECircle.outterRect(buffer: ERect = ERect()): ERect {
    val width = radius * 2
    buffer.width = width
    buffer.height = width
    buffer.center = center
    return buffer
}

fun ECircle.innerRect(buffer: ERect = ERect()): ERect {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    buffer.width = width
    buffer.height = width
    buffer.center = center
    return buffer
}