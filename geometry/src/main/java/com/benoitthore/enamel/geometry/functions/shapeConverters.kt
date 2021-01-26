package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.line
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalMutable
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import kotlin.math.hypot
import kotlin.math.sqrt


fun EShape<*, *>.toLine(from: EAlignment, to: EAlignment) =
    toLine(from.namedPoint, to.namedPoint)

/**
 * Input treated as anchor (0,0 top left - 1,1 bottom right)
 */
fun EShape<*, *>.toLine(from: EPoint, to: EPoint) = toLine(from.x, from.y, to.x, to.y)

/**
 * Input treated as anchor (0,0 top left - 1,1 bottom right)
 */
fun EShape<*, *>.toLine(
    fromX: Number,
    fromY: Number,
    toX: Number,
    toY: Number
): ELine = pointAtAnchor(fromX, fromY) line pointAtAnchor(toX, toY)


fun ECircle.pointAtAngle(angle: EAngle, target: EPointMutable = MutablePoint()): EPoint =
    target.set(angle, radius).selfOffset(center)

fun ECircle.toListOfPoint(
    list: MutableList<EPointMutable>,
    startAt: EAngle? = null,
    distanceList: List<Number>? = null
): List<EPoint> {
    if (list.isEmpty()) {
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
        MutableList(distanceList.size) { MutablePoint() }, startAt, distanceList
    )

fun ECircle.toListOfPoint(
    numberOfPoint: Int,
    startAt: EAngle? = null
) =
    toListOfPoint(
        MutableList(numberOfPoint) { MutablePoint() }, startAt
    )

fun EPoint.toCircle(radius: Number = 0f, target: ECircleMutable = MutableCircle()): ECircle {
    target.center.set(this)
    target.radius = radius.f
    return target
}

fun List<EPoint>.toCircles(
    radius: Number,
    target: List<ECircleMutable> = MutableList(size) { MutableCircle() }
): List<ECircle> {
    forEachIndexed { i, p ->
        p.toCircle(radius, target[i])
    }
    return target
}

fun ESize.toRect(target: ERectMutable = MutableRect()) = target.setOriginSize(0, 0, width, height)

fun EShape<*, *>.innerCircle(target: ECircleMutable = MutableCircle()): ECircle {
    getCenter(target.center) // set circles center to rect center
    target.radius = (if (width > height) height else width) / 2f // TODO Repalce that with minmax
    return target
}

fun EShape<*, *>.outerCircle(target: ECircleMutable = MutableCircle()): ECircle {
    getCenter(target.center) // set circles center to rect center
    target.radius = hypot(width.d, height.d).f / 2f
    return target
}


fun EShape<*, *>.innerOval(target: EOvalMutable = MutableOval()): EOval {
    target._setBounds(
        left = left,
        top = top,
        right = right,
        bottom = bottom
    )
    return target
}

fun EShape<*, *>.outerOval(target: EOval = Oval()): EOval {
    TODO()
    return target
}

fun ECircle.outerRect(target: ERectMutable = MutableRect()): ERectMutable =
    target.apply { this@outerRect.getBounds(target) }

fun ECircle.innerRect(target: ERectMutable = MutableRect()): ERectMutable {
    val width = sqrt((2 * radius * radius).toDouble()).f
    target.width = width
    target.height = width
    target._setCenter(center)
    return target
}