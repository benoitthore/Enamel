package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.Allocates
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.interfaces.HasCenter
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.radians
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point._angleTo
import com.benoitthore.enamel.geometry.primitives.point._offsetAngle
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext
import kotlin.math.max
import kotlin.math.min


/*
TODO Make allocation free
 */
interface ELine : ELinearFunction, ESVG, HasCenter,
    HasBounds {

    val start: EPoint
    val end: EPoint

    val length
        get() = start.distanceTo(end).f

    fun angle(target: EAngleMutable) = angleRadians.radians(target = target)
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

    override val centerX: Float
        get() = (start.x + end.x) / 2
    override val centerY: Float
        get() = (start.y + end.y) / 2

    override val left: Float
        get() = min(start.x, end.x)
    override val top: Float
        get() = min(start.y, end.y)
    override val right: Float
        get() = max(start.x, end.x)
    override val bottom: Float
        get() = max(start.y, end.y)

    val isTLBR get() = (start.x < end.x && start.y < end.y) || (end.x < start.x && end.y < start.y)
    val isBRTL get() = !isTLBR

    override fun addTo(context: SVGContext) {
        with(context) {
            move(start)
            line(end)
        }
    }

    fun pointAt(at: Float, target: EPointMutable): EPointMutable =
        start.offsetTowards(end, length * at, target = target)

    @Allocates
    fun pointFrom(distance: Number, from: Float, target: EPointMutable): EPoint {
        val opposite = pointAt(from.opposite(), target = E.PointMutable())
        return target.set(opposite.offsetFrom(pointAt(from, target = E.PointMutable()), distance))
    }

    @Allocates
    fun pointTowards(distance: Number, towards: Float, target: EPointMutable) =
        target.set(
            pointAt(towards.opposite(), target = E.PointMutable()).offsetTowards(
                pointAt(
                    towards,
                    target = E.PointMutable()
                ), distance
            )
        )

    fun extrapolateFrom(distance: Number, from: Number, target: EPointMutable): EPoint {
        val from = from.toFloat()

        val froPointMutable = pointAt(from.opposite(), target = target)

        val totalDistance = length + distance.f

        return froPointMutable._offsetAngle(angleRadians, totalDistance, target = froPointMutable)
    }

    fun isParallel(other: ELineMutable) = angleRadians == angleRadians

    fun center(target: EPointMutable) = pointAt(0.5f, target = target)

    fun rotate(
        offsetAngle: EAngleMutable,
        around: EPoint = center(E.PointMutable()),
        target: ELineMutable
    ): ELineMutable {
        start.rotateAround(offsetAngle, around, target = target.start)
        end.rotateAround(offsetAngle, around, target = target.end)
        return target
    }

    fun expanded(distance: Number, from: Number = 0f, target: ELineMutable): ELineMutable {
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
        distanceFroLineMutable: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPointMutable
    ): EPoint {
        val x = pointTowards(distanceTowardsEndPoint, towards, target = E.PointMutable())
        return target.set(
            x.offsetAngle(
                angle = angle(E.AngleMutable()) - 90.degrees(),
                distance = distanceFroLineMutable
            )
        )
    }

    fun perpendicularPointRight(
        distanceFroLineMutable: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPointMutable
    ) =
        perpendicularPointLeft(
            -distanceFroLineMutable.f,
            distanceTowardsEndPoint,
            towards,
            target = target
        )

    fun perpendicular(
        distance: Number,
        towards: Float,
        leftLength: Number,
        rightLength: Number,
        target: ELineMutable
    ): ELine {
        perpendicularPointLeft(
            distanceFroLineMutable = leftLength,
            distanceTowardsEndPoint = distance,
            towards = towards,
            target = target.start
        )
        perpendicularPointRight(
            distanceFroLineMutable = rightLength,
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
        target: ELineMutable
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
        target: ELineMutable
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

    fun perpendicular(at: Number, length: Number, target: ELineMutable) = perpendicular(
        at = at,
        leftLength = length.f / 2f,
        rightLength = length.f / 2f,
        target = target
    )

    fun parallel(distance: Number, target: ELineMutable): ELine {

        TODO()

        return target
    }


    private fun Float.opposite() = 1f - this
}
