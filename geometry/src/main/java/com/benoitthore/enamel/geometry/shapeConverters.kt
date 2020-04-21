package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.EPoint


fun EPoint.toCircle(radius: Number, target: ECircleMutable = ECircleMutable()): ECircleMutable {
    target.center.set(this)
    target.radius = radius.f
    return target
}


fun List<EPointMutable>.toCircles(radius: Number, target: List<ECircleMutable> = MutableList(size) { ECircleMutable() }): List<ECircleMutable> {
    forEachIndexed { i, p ->
        p.toCircle(radius, target[i])
    }
    return target
}

fun ESize.toRect(target: ERectMutable = ERectMutable()) = target.set(0, 0, width, height)


fun ERect.innerCircle(target: ECircleMutable = ECircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = size.min / 2
    return target
}


fun ERect.outterCircle(target: ECircleMutable = ECircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = size.diagonal / 2
    return target
}

fun ECircle.outterRect(target: ERectMutable = ERectMutable()): ERectMutable {
    val width = radius * 2
    target.width = width
    target.height = width
    target.center = center
    return target
}

fun ECircle.innerRect(target: ERectMutable = ERectMutable()): ERectMutable {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    target.width = width
    target.height = width
    target.center = center
    return target
}