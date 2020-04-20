package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.primitives.*


/*
TODO Make mutable/immutable version
TODO Make allocation free
 */
open class ELine(open val start: EPoint = EPoint.zero, open val end: EPoint = EPoint.zero) {

    private fun Float.opposite() = 1f - this

    init {
        allocateDebugMessage()
    }

    companion object {
        val unit = ELine(start = EPointMutable.zero, end = EPointMutable.unit)
        val zero = ELine(start = EPointMutable.zero, end = EPointMutable.zero)
    }

    val length
        get() = start.distanceTo(end).f
    val angle
        get() = start.angleTo(end)
    val x1
        get() = start.x
    val x2
        get() = end.x
    val y1
        get() = start.y
    val y2
        get() = end.y

    val linearFunction: ELinearFunction
        get() = run {
            val a = (end.y - start.y) / (end.x - start.x)
            // y = ax + b
            // So -> b  = y - ax
            val b = start.y - a * start.x
            ELinearFunction(a, b)
        }

    fun pointAt(at: Float): EPoint = start.offsetTowards(end, length * at)

    fun pointFrom(distance: Number, from: Float): EPoint {
        val opposite = pointAt(from.opposite())
        return opposite.offsetFrom(pointAt(from), distance)
    }

    fun pointTowards(distance: Number, towards: Float) =
        pointAt(towards.opposite()).offsetTowards(pointAt(towards), distance)

    fun extrapolateFrom(distance: Number, from: Float): EPoint {
        val fromPoint = pointAt(from.opposite())
        val towards = pointAt(from)
        val totalDistance = length + distance.f
        return fromPoint.offsetTowards(towards, totalDistance)
    }

    fun isParallel(other: ELineMutable) = linearFunction.a == other.linearFunction.a

    fun center() = pointAt(0.5f)

    fun rotate(offsetAngle: EAngleMutable, around: EPoint = center()): ELineMutable {
        val newStart = start.rotateAround(offsetAngle, around)
        val newEnd = end.rotateAround(offsetAngle, around)
        return newStart line newEnd
    }

    fun expanded(distance: Number, from: Float): ELine {
        val start = pointAt(from.opposite())
        return start line extrapolateFrom(distance, from)
    }

    fun toListOfPoints(number: Int): List<EPoint> {
        return when (number) {
            0 -> emptyList()
            1 -> listOf(start)
            2 -> listOf(start, end)
            else -> {
                val distance = length.i
                val step = (distance / (number - 1)).i
                (0..distance step step).map { currentDistance ->
                    start.offsetTowards(end, currentDistance)
                }
            }
        }
    }

    fun perpendicularPointLeft(
        distanceFromLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float
    ): EPoint {
        val x = pointTowards(distanceTowardsEndPoint, towards)
        return x.offsetAngle(angle = angle - 90.degrees(), distance = distanceFromLine)
    }

    fun perpendicularPointRight(
        distanceFromLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float
    ) =
        perpendicularPointLeft(-distanceFromLine.f, distanceTowardsEndPoint, towards)

    fun perpendicular(
        distance: Number,
        towards: Float,
        leftLength: Number,
        rightLength: Number
    ): ELine {
        val start = perpendicularPointLeft(
            distanceFromLine = leftLength,
            distanceTowardsEndPoint = distance,
            towards = towards
        )
        val end = perpendicularPointRight(
            distanceFromLine = rightLength,
            distanceTowardsEndPoint = distance,
            towards = towards
        )

        return start line end
    }

    fun perpendicular(
        distance: Number,
        towards: Float,
        length: Number
    ) =
        perpendicular(
            distance = distance,
            towards = towards,
            leftLength = length.f / 2f,
            rightLength = length.f / 2f
        )

    fun perpendicular(at: Float, leftLength: Number, rightLength: Number): ELine {
        val offset = length * at
        return perpendicular(
            distance = offset,
            towards = 1f,
            leftLength = leftLength,
            rightLength = rightLength
        )
    }

    fun perpendicular(at: Float, length: Number) = perpendicular(
        at = at,
        leftLength = length.f / 2f,
        rightLength = length.f / 2f
    )

    fun parallel(distance: Number, buffer: ELineMutable): ELine {

        TODO()

        return buffer
    }

    override fun toString(): String {
        return "($start, $end)"
    }


}

class ELineMutable(
    override val start: EPointMutable = EPointMutable.zero,
    override val end: EPointMutable = EPointMutable.zero
) : ELine(start, end), Resetable {

    fun set(start: EPoint = this.start, end: EPoint = this.end) =
        set(start.x, start.y, end.x, end.y)

    fun set(
        x1: Number = start.x,
        y1: Number = start.y,
        x2: Number = end.x,
        y2: Number = end.y
    ) = apply {
        start.x = x1.f
        start.y = y1.f

        end.x = x2.f
        end.y = y2.f
    }

    fun selfOffset(xOff: Number, yOff: Number) = apply {
        start.selfOffset(xOff, yOff)
        end.selfOffset(xOff, yOff)
    }

    fun selfOffset(p: EPoint) = selfOffset(p.x, p.y)
    fun selfScale(width: Number, height: Number): ELineMutable {
        start.x *= width.f
        end.x *= width.f

        start.y *= height.f
        end.y *= height.f
        return this
    }

    override fun reset() {
        start.reset()
        end.reset()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ELineMutable

        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }


}

infix fun EPoint.line(end: EPoint) = ELine(start = this, end = end)
infix fun EPointMutable.line(end: EPointMutable) = ELineMutable(start = this, end = end)

/// CONVERT
fun List<EPoint>.toListOfLines(): List<ELine> {
    val ret = mutableListOf<ELine>()
    forEachIndexed { i, curr ->
        if (i > 1) {
            val prev = get(i - 1)
            ret.add(prev line curr)
        }
    }
    return ret
}

//
// TODO

fun EPoint.closetPointOnSegment(line: ELine) = line.closetPointOnSegment(this)
fun ELine.closetPointOnSegment(point: EPoint) = getClosestPointOnSegment(
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

    if (xDelta == 0.0f && yDelta == 0.0f) {
        throw IllegalArgumentException("Segment start equals segment end")
    }

    val u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta)

    return when {
        u < 0 -> EPoint(sx1, sy1)
        u > 1 -> EPoint(sx2, sy2)
        else -> EPoint(Math.round(sx1 + u * xDelta), Math.round(sy1 + u * yDelta))
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
    val area = Math.sqrt((s * (s - AB) * (s - BC) * (s - AC)).toDouble()).toFloat()

    // but also area == (BC * AD) / 2
    // BC * AD == 2 * area
    // AD == (2 * area) / BC
    // TODO: check if BC == 0
    return 2 * area / BC
}


val List<ELine>.length: Float get() = sumByDouble { it.length.toDouble() }.toFloat()


operator fun ELine.component1() = start
operator fun ELine.component2() = end

fun List<EPoint>.pointAtFraction(fraction: Number, buffer: EPointMutable = EPointMutable()) =
    pointAtDistance(fraction.toFloat() * length, buffer)

fun List<EPoint>.pointAtDistance(
    distance: Number,
    buffer: EPointMutable = EPointMutable()
): EPointMutable {
    var last: EPoint? = null
    val distance = distance.toFloat()
    var remainingDistance = distance

    forEach { p ->
        last?.let { last ->
            val distanceToNext = last.distanceTo(p)
            if (remainingDistance - distanceToNext < 0) {
                return last.offsetTowards(p, remainingDistance, buffer)
            }
            remainingDistance -= distanceToNext
        }
        last = p
    }

    return last().toMutable(buffer)
}



