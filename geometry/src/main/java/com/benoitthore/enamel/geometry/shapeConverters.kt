package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.EPoint


fun EPoint.toCircle(radius: Number, target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    target.center.set(this)
    target.radius = radius.f
    return target
}


fun List<EPointMutable>.toCircles(radius: Number, target: List<ECircleMutable> = MutableList(size) { E.CircleMutable() }): List<ECircleMutable> {
    forEachIndexed { i, p ->
        p.toCircle(radius, target[i])
    }
    return target
}

fun ESize.toRect(target: ERectMutable = E.RectMutable()) = target.set(0, 0, width, height)


fun ERect.innerCircle(target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = size.min / 2
    return target
}


fun ERect.outterCircle(target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = size.diagonal / 2
    return target
}

fun ECircle.outterRect(target: ERectMutable = E.RectMutable()): ERectMutable {
    val width = radius * 2
    target.width = width
    target.height = width
    target.setCenter(center)
    return target
}

fun ECircle.innerRect(target: ERectMutable = E.RectMutable()): ERectMutable {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    target.width = width
    target.height = width
    target.setCenter(center)
    return target
}