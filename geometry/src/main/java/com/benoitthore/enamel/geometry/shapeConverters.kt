package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import kotlin.math.hypot

fun ECircle.pointAtAngle(angle: EAngle, target: EPoint = E.Point()): EPoint =
    target.set(angle, radius).selfOffset(center)

fun ECircle.toListOfPoint(
    list: MutableList<EPoint>,
    startAt: EAngle? = null,
    distanceList: List<Number>? = null
): List<EPoint> {
    if (list.isEmpty()) { // Don't divide by zero
        return list
    }

    val degreesPerStep = 360f / list.size
    val extra = startAt?.degrees?.i ?: 0
    val froAngle = 0f + extra

    var currAngle = froAngle
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
        MutableList(distanceList.size) { E.Point() }, startAt, distanceList
    )

fun ECircle.toListOfPoint(
    numberOfPoint: Int,
    startAt: EAngle? = null
) =
    toListOfPoint(
        MutableList(numberOfPoint) { E.Point() }, startAt
    )

fun EPoint.toCircle(radius: Number = 0f, target: ECircle = E.Circle()): ECircle {
    target.center.set(this)
    target.radius = radius.f
    return target
}

fun List<EPoint>.toCircles(
    radius: Number,
    target: List<ECircle> = MutableList(size) { E.Circle() }
): List<ECircle> {
    forEachIndexed { i, p ->
        p.toCircle(radius, target[i])
    }
    return target
}

fun ESize.toRect(target: ERect = E.Rect()) = target.setOriginSize(0, 0, width, height)

fun EShape<*>.innerCircle(target: ECircle = E.Circle()): ECircle {
    getCenter(target.center) // set circles center to rect center
    target.radius = (if (width > height) height else width) / 2f // TODO Repalce that with minmax
    return target
}

fun EShape<*>.outerCircle(target: ECircle = E.Circle()): ECircle {
    getCenter(target.center) // set circles center to rect center
    target.radius = hypot(width.d, height.d).f / 2f
    return target
}


fun EShape<*>.innerOval(target: EOval = E.Oval()): EOval {
    target.setBounds(left,top,right,bottom)
    return target
}

fun EShape<*>.outerOval(target: EOval = E.Oval()): EOval {
    TODO()
    return target
}

fun ECircle.outerRect(target: ERect = E.Rect()): ERect =
    target.apply { this@outerRect.getBounds(target) }

fun ECircle.innerRect(target: ERect = E.Rect()): ERect {
    val width = Math.sqrt((2 * radius * radius).toDouble()).f
    target.width = width
    target.height = width
    target.setCenter(center)
    return target
}