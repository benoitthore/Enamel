package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.set
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.interfaces.bounds.center
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import kotlin.math.hypot

fun ECircle.pointAtAngle(angle: EAngle, target: EPointMutable = E.PointMutable()): EPointMutable =
    target.set(angle, radius).selfOffset(center)

fun ECircle.toListOfPoint(
    list: MutableList<EPointMutable>,
    startAt: EAngle? = null,
    distanceList: List<Number>? = null
): List<EPointMutable> {
    if (list.isEmpty()) { // Don't divide by zero
        return list
    }

    val degreesPerStep = 360f / list.size
    val extra = startAt?.degrees?.i ?: 0
    val froAngleMutable = 0f + extra

    var currAngle = froAngleMutable
    var i = 0
    while (i < list.size) {
        val angle = currAngle.degrees()

        val point = list[i]
        point.set(center)
        val distance =
            if (distanceList != null && distanceList.isNotEmpty()) {
                distanceList[i % distanceList.size]
            } else {
                radius
            }
        point.offsetAngle(angle, distance, point)
        list[i] = point

        currAngle += degreesPerStep
        i++
    }

    return list
}

fun ECircle.toListOfPoint(
    distanceList: List<Number>,
    startAt: EAngle? = null
) =
    toListOfPoint(
        MutableList(distanceList.size) { E.PointMutable() }, startAt, distanceList
    )

fun ECircle.toListOfPoint(
    numberOfPoint: Int,
    startAt: EAngle? = null
) =
    toListOfPoint(
        MutableList(numberOfPoint) { E.PointMutable() }, startAt
    )

fun EPoint.toCircle(radius: Number = 0f, target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    target.center.set(this)
    target.radius = radius.f
    return target
}

fun List<EPointMutable>.toCircles(
    radius: Number,
    target: List<ECircleMutable> = MutableList(size) { E.CircleMutable() }
): List<ECircleMutable> {
    forEachIndexed { i, p ->
        p.toCircle(radius, target[i])
    }
    return target
}

fun ESize.toRect(target: ERectMutable = E.RectMutable()) = target.set(0, 0, width, height)

fun HasBounds<*,*>.innerCircle(target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = (if (width > height) height else width) / 2f // TODO Repalce that with minmax
    return target
}

fun HasBounds<*,*>.outerCircle(target: ECircleMutable = E.CircleMutable()): ECircleMutable {
    center(target.center) // set circles center to rect center
    target.radius = hypot(width.d, height.d).f / 2f
    return target
}

fun ECircle.outerRect(target: ERectMutable = E.RectMutable()): ERectMutable =
    target.apply { this@outerRect.getBounds(target) }

fun ECircle.innerRect(target: ERectMutable = E.RectMutable()): ERectMutable {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    target.width = width
    target.height = width
    target.setCenter(center)
    return target
}