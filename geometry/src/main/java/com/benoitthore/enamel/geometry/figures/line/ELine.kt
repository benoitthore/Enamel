@file:JvmName("ELineKt")

package com.benoitthore.enamel.geometry.figures.line

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.Allocates
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.radians
import com.benoitthore.enamel.geometry.primitives.linearfunction.ELinearFunction
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point._angleTo
import com.benoitthore.enamel.geometry.primitives.point._offsetAngle
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ELine : EShape<ELine, ELineMutable>, ELinearFunction {

    val start: EPoint
    val end: EPoint

    override fun _copy(): ELine = Line(this)

    val length
        get() = start.distanceTo(end).f

    fun angle(target: EAngleMutable = MutableAngle()) = angleRadians.radians(target = target)
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

    fun pointAt(at: Float, target: EPointMutable = MutablePoint()): EPointMutable =
        start.offsetTowards(end, length * at, target = target)

    @Allocates
    fun pointFrom(
        distance: Number,
        from: Float,
        target: EPointMutable = MutablePoint()
    ): EPointMutable {
        val opposite = pointAt(from.opposite(), target = MutablePoint())
        return target.set(opposite.offsetFrom(pointAt(from, target = MutablePoint()), distance))
    }

    @Allocates
    fun pointTowards(distance: Number, towards: Float, target: EPointMutable = MutablePoint()) =
        target.set(
            pointAt(towards.opposite(), target = MutablePoint()).offsetTowards(
                pointAt(
                    towards,
                    target = MutablePoint()
                ), distance
            )
        )

    fun extrapolateFrom(
        distance: Number,
        from: Number,
        target: EPointMutable = MutablePoint()
    ): EPoint {
        val from = from.toFloat()

        val froPoint = pointAt(from.opposite(), target = target)

        val totalDistance = length + distance.f

        return froPoint._offsetAngle(angleRadians, totalDistance, target = froPoint)
    }

    fun isParallel(other: ELine) = angleRadians == angleRadians

    fun rotate(
        offsetAngle: EAngle,
        around: EPoint,
        target: ELineMutable = MutableLine()
    ): ELine = rotate(offsetAngle, around.x, around.y, target)

    fun rotate(
        offsetAngle: EAngle,
        aroundX: Number = centerX,
        aroundY: Number = centerY,
        target: ELineMutable = MutableLine()
    ): ELine {
        start.rotateAround(offsetAngle, aroundX, aroundY, target = target.start)
        end.rotateAround(offsetAngle, aroundX, aroundY, target = target.end)
        return target
    }

    fun expand(distance: Number, from: Number = 0f, target: ELineMutable = MutableLine()): ELine {
        val from = from.toFloat()
        pointAt(from, target = target.start)
        extrapolateFrom(distance, from.opposite(), target = target.end)
        return target
    }

    fun toListOfPoints(list: MutableList<EPointMutable>): List<EPointMutable> {
        when (list.size) {
            0 -> {
            }
            1 -> list[0].set(start)
            2 -> {
                list[0].set(start)
                list[1].set(end)
            }
            else -> {
                val distance = length.i
                val step = (distance / (list.size - 1)).i
                var currentDistance = 0f
                list.forEachIndexed { index, point ->

                    start.offsetTowards(end, currentDistance, target = point)
                    currentDistance += step
                }
                TODO("TEST")
            }
        }
        return list
    }

    fun toListOfPoints(number: Int): List<EPointMutable> = toListOfPoints(
        MutableList(number) { MutablePoint() }
    )

    @Allocates
    fun perpendicularPointLeft(
        distanceFroLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPointMutable = MutablePoint()
    ): EPointMutable {
        val x = pointTowards(distanceTowardsEndPoint, towards, target = MutablePoint())
        return target.set(
            x.offsetAngle(
                angle = angle(MutableAngle()) - 90.degrees(),
                distance = distanceFroLine
            )
        )
    }

    fun perpendicularPointRight(
        distanceFroLine: Number,
        distanceTowardsEndPoint: Number,
        towards: Float,
        target: EPointMutable = MutablePoint()
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
        target: ELineMutable = MutableLine()
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
        target: ELineMutable = MutableLine()
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
        target: ELineMutable = MutableLine()
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

    fun perpendicular(
        at: Number,
        length: Number,
        target: ELineMutable = MutableLine()
    ) = perpendicular(
        at = at,
        leftLength = length.f / 2f,
        rightLength = length.f / 2f,
        target = target
    )

    fun parallel(distance: Number, target: ELineMutable = MutableLine()): ELine {
        TODO()
    }


    private inline fun Float.opposite() = 1f - this
}

interface ELineMutable : ELine, EShapeMutable<ELine, ELineMutable> {
    override val start: EPointMutable
    override val end: EPointMutable

    override fun _copy(): ELineMutable = MutableLine(this)
    override fun toMutable(): ELineMutable = _copy()
    override fun toImmutable(): ELine = _copy()
    override fun set(other: ELine)  = apply {
        start.set(other.start)
        end.set(other.end)
    }


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

    fun selfRotate(
        offsetAngle: EAngle,
        aroundX: Number = centerX,
        aroundY: Number = centerY,
        target: ELine
    ) = apply { rotate(offsetAngle, centerX, centerY, this) }

    fun selfRotate(
        offsetAngle: EAngle,
        around: EPoint,
        target: ELine
    ) = apply { rotate(offsetAngle, around, this) }

    fun selfExpand(distance: Number, from: Number = 0f) =
        expand(distance, from, this)

}