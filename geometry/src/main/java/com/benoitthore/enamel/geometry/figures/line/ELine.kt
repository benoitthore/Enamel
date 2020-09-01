@file:JvmName("ELineKt")

package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.Allocates
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.radians
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point._angleTo
import com.benoitthore.enamel.geometry.primitives.point._offsetAngle
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ELine : EShape, ELinearFunction {

    val start: EPoint
    val end: EPoint

    override fun _copy(): ELine = E.Line(this)
    fun set(other : ELine) : ELine


    val length
        get() = start.distanceTo(end).f

    fun angle(target: EAngle) = angleRadians.radians(target = target)
    val x1
        get() = start.x
    val x2
        get() = end.x
    val y1
        get() = start.y
    val y2
        get() = end.y

    private val angleRadians get() = start._angleTo(end.x, end.y)

    override val slope: Float
        get() = (end.y - start.y) / (end.x - start.x)

    override val yIntercept: Float
        get() = start.y - a * start.x


    val isTLBR get() = (start.x < end.x && start.y < end.y) || (end.x < start.x && end.y < start.y)
    val isBRTL get() = !isTLBR

    override fun addTo(context: SVGContext) {
        with(context) {
            move(start)
            line(end)
        }
    }

    fun pointAt(at: Float, target: EPoint): EPoint =
        start.offsetTowards(end, length * at, target = target)

    @Allocates
    fun pointFrom(distance: Number, from: Float, target: EPoint): EPoint {
        val opposite = pointAt(from.opposite(), target = E.Point())
        return target.set(opposite.offsetFrom(pointAt(from, target = E.Point()), distance))
    }

    @Allocates
    fun pointTowards(distance: Number, towards: Float, target: EPoint) =
        target.set(
            pointAt(towards.opposite(), target = E.Point()).offsetTowards(
                pointAt(
                    towards,
                    target = E.Point()
                ), distance
            )
        )

    fun extrapolateFrom(distance: Number, from: Number, target: EPoint): EPoint {
        val from = from.toFloat()

        val froPoint = pointAt(from.opposite(), target = target)

        val totalDistance = length + distance.f

        return froPoint._offsetAngle(angleRadians, totalDistance, target = froPoint)
    }

    fun isParallel(other: ELine) = angleRadians == angleRadians

    fun rotate(
        offsetAngle: EAngle,
        around: EPoint = getCenter(E.Point()),
        target: ELine
    ): ELine {
        start.rotateAround(offsetAngle, around, target = target.start)
        end.rotateAround(offsetAngle, around, target = target.end)
        return target
    }

    fun expand(distance: Number, from: Number = 0f, target: ELine): ELine {
        val from = from.toFloat()
        pointAt(from, target = target.start)
        extrapolateFrom(distance, from.opposite(), target = target.end)
        return target
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

    @Allocates
    fun perpendicularPointLeft(
        distanceFroLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPoint
    ): EPoint {
        val x = pointTowards(distanceTowardsEndPoint, towards, target = E.Point())
        return target.set(
            x.offsetAngle(
                angle = angle(E.Angle()) - 90.degrees(),
                distance = distanceFroLine
            )
        )
    }

    fun perpendicularPointRight(
        distanceFroLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPoint
    ) =
        perpendicularPointLeft(
            -distanceFroLine.f,
            distanceTowardsEndPoint,
            towards,
            target = target
        )

    fun perpendicular(
        distance: Number,
        towards: Float,
        leftLength: Number,
        rightLength: Number,
        target: ELine
    ): ELine {
        perpendicularPointLeft(
            distanceFroLine = leftLength,
            distanceTowardsEndPoint = distance,
            towards = towards,
            target = target.start
        )
        perpendicularPointRight(
            distanceFroLine = rightLength,
            distanceTowardsEndPoint = distance,
            towards = towards,
            target = target.end
        )

        return target
    }

    fun perpendicular(
        distance: Number,
        towards: Float,
        length: Number,
        target: ELine
    ) =
        perpendicular(
            distance = distance,
            towards = towards,
            leftLength = length.f / 2f,
            rightLength = length.f / 2f,
            target = target
        )

    fun perpendicular(
        at: Number, leftLength: Number, rightLength: Number,
        target: ELine
    ): ELine {
        val offset = length * at.toFloat()
        return perpendicular(
            distance = offset,
            towards = 1f,
            leftLength = leftLength,
            rightLength = rightLength,
            target = target
        )
    }

    fun perpendicular(at: Number, length: Number, target: ELine) = perpendicular(
        at = at,
        leftLength = length.f / 2f,
        rightLength = length.f / 2f,
        target = target
    )

    fun parallel(distance: Number, target: ELine): ELine {

        TODO()

        return target
    }


    private fun Float.opposite() = 1f - this
}

fun <T : ELine> T.set(start: EPoint = this.start, end: EPoint = this.end) =
    set(start.x, start.y, end.x, end.y)

fun <T : ELine> T.set(
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

fun <T : ELine> T.selfRotate(
    offsetAngle: EAngle,
    around: EPoint = getCenter(E.Point()),
    target: ELine
) = apply { rotate(offsetAngle, around, this) }

fun <T : ELine> T.selfExpand(distance: Number, from: Number = 0f) =
    expand(distance, from, this)
