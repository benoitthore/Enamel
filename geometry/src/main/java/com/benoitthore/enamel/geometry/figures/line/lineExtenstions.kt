package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.length
import com.benoitthore.enamel.geometry.primitives.point
import com.benoitthore.enamel.geometry.toMutable
import kotlin.math.*

infix fun EPoint.line(end: EPoint) = E.Line(start = this, end = end)
infix fun EPointMutable.line(end: EPointMutable) = E.LineMutable(start = this, end = end)

fun List<EPoint>.toListOfLines(): List<ELine> {
    val ret = mutableListOf<ELine>()
    forEachIndexed { i, curr ->
        if (i > 1) {
            val prev = get(i - 1)
            ret.add(E.Line(prev, curr))
        }
    }
    return ret
}

//

fun EPoint.closetPointOnSegment(line: ELine) = line.closetPointOnSegment(this)
fun ELine.closetPointOnSegment(point: EPoint) =
    getClosestPointOnSegment(
        start.x, start.y,
        end.x, end.y,
        point.x, point.y
    )

/**
 * Returns closest point on segment to point
 * @param sx1 - segment x coord 1
 * @param sy1 - segment y coord 1
 * @param sx2 - segment x coord 2
 * @param sy2 - segment y coord 2
 * @param px - point x coord
 * @param py - point y coord
 * @return closets point on segment to point
 */
private fun getClosestPointOnSegment(
    sx1: Float,
    sy1: Float,
    sx2: Float,
    sy2: Float,
    px: Float,
    py: Float
): EPoint {
    val xDelta = sx2 - sx1
    val yDelta = sy2 - sy1

    if (xDelta == 0.0f && yDelta == 0.0f) { // sx2 == sx1 && sy2 && sy1
        return sx1 point sx2
    }

    val u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta)

    return when {
        u < 0 -> E.Point(sx1, sy1)
        u > 1 -> E.Point(sx2, sy2)
        else -> E.Point(round(sx1 + u * xDelta), round(sy1 + u * yDelta))
    }
}

//
fun EPoint.distanceTo(line: ELine) = line.distanceTo(this)

fun ELine.distanceTo(point: EPoint): Float {
    // A - the standalone point (x, y)
    // B - start point of the line segment (x1, y1)
    // C - end point of the line segment (x2, y2)
    // D - the crossing point between line from A to BC
    val A = point
    val B = start
    val C = end


    val AB = A.distanceTo(B)
    val BC = B.distanceTo(C)
    val AC = A.distanceTo(C)

    // Heron's formula
    val s = (AB + BC + AC) / 2
    val area = sqrt((s * (s - AB) * (s - BC) * (s - AC)).toDouble()).toFloat()


    return if (BC == 0f) 0f else 2 * area / BC
}


val List<ELine>.length: Float get() = sumByDouble { it.length.toDouble() }.toFloat()


operator fun ELine.component1() = start
operator fun ELine.component2() = end

fun List<EPoint>.pointAtFraction(fraction: Number, target: EPointMutable = E.PointMutable()) =
    pointAtDistance(fraction.toFloat() * length, target)

fun List<EPoint>.pointAtDistance(
    distance: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable {
    var last: EPoint? = null
    val distance = distance.toFloat()
    var remainingDistance = distance

    forEach { p ->
        last?.let { last ->
            val distanceToNext = last.distanceTo(p)
            if (remainingDistance - distanceToNext < 0) {
                return last.offsetTowards(p, remainingDistance, target)
            }
            remainingDistance -= distanceToNext
        }
        last = p
    }

    return last().toMutable(target)
}



