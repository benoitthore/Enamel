package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.core.Resetable
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.allocateDebugMessage
import com.thorebenoit.enamel.kotlin.geometry.primitives.*

open class ECircleType(open val center: EPointType = EPointType(), open val radius: Float = 0f) {

    init {
        allocateDebugMessage()
    }

    open val x: Float get() = center.x

    open val y: Float get() = center.y

    fun toMutable() = ECircle(center = center.toMutable(), radius = radius)
    fun toImmutable() = ECircleType(center = center.toImmutable(), radius = radius)
}

class ECircle(override val center: EPoint = EPoint(), override var radius: Float = 0f) :
    ECircleType(center, radius), Resetable {
    constructor(center: EPoint = EPoint(), radius: Number) : this(center, radius.f)

    fun copy(buffer: ECircle = ECircle()) = ECircle(center.copy(buffer.center), radius)

    override var x: Float
        get() = super.x
        set(value) {
            center.x = value
        }
    override var y: Float
        get() = super.y
        set(value) {
            center.y = value
        }

    override fun reset() {
        set(0, 0, 0)
    }

    fun set(center: EPointType, radius: Number) = set(center.x, center.y, radius)

    fun set(x: Number, y: Number, radius: Number): ECircle {
        this.center.set(x, y)
        this.radius = radius.f
        return this
    }

    fun toListOfPoint(
        list: MutableList<EPoint>,
        startAt: EAngleType? = null,
        distanceList: List<Number>? = null
    ): List<EPoint> {
        if (list.isEmpty()) { // Don't divide by zero
            return list
        }

        val degreesPerStep = 360f / list.size
        val extra = startAt?.degrees?.i ?: 0
        val fromAngle = 0f + extra

        var currAngle = fromAngle
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

    fun toListOfPoint(numberOfPoint: Int, startAt: EAngle? = null, distanceList: List<Number>? = null) = toListOfPoint(
        MutableList(numberOfPoint) { EPoint() }, startAt, distanceList
    )


    fun inset(margin: Number) = set(center, radius - margin.f)
    fun expand(margin: Number) = inset(-margin.f)

    fun offset(x: Number, y: Number) = set(this.x + x.f, this.y + y.f, radius)
    fun offset(n: Number) = offset(n, n)
    fun offset(other: EPoint) = offset(other.x, other.y)


    private fun pointAtAnchor(x: Number, y: Number, buffer: EPoint = EPoint()): EPoint {
        val origin = buffer.set(center.x - radius, center.y - radius)
        val size = radius * 2
        return EPoint(x = origin.x + size * x.f, y = origin.y + size * y.f)
    }

    private fun pointAtAnchor(anchor: EPoint, buffer: EPoint = EPoint()) = pointAtAnchor(anchor.x, anchor.y, buffer)
    fun scaledAnchor(to: Number, x: Number, y: Number, buffer: EPoint = EPoint()) =
        scaledRelative(to, relativeTo = pointAtAnchor(x, y, buffer))

    fun scaledAnchor(to: Number, anchor: EPointType = EPointType.half, buffer: EPoint = EPoint()) =
        scaledAnchor(to, anchor.x, anchor.y, buffer)

    fun scaledRelative(to: Number, relativeTo: EPointType = EPointType.half): ECircle {
        val to = to.f
        val newCenterX = center.x + (relativeTo.x - center.x) * (1 - to)
        val newCenterY = center.x + (relativeTo.x - center.x) * (1 - to)
        return set(newCenterX, newCenterY, radius * to)
    }

    fun resized(newRadius: Number) = set(center = center, radius = newRadius)
    fun resizedBy(extraRadius: Number) = set(center = center, radius = radius + extraRadius.f)

    fun intersects(other: ECircle) = this.center.distanceTo(other.center) < other.radius + this.radius

    fun intersects(list: List<ECircle>): Boolean {
        for (other in list) {
            if (other !== this && other.intersects(this)) {
                return true
            }
        }
        return false

    }

    fun contains(x: Number, y: Number): Boolean = center.distanceTo(x, y) < radius
    fun contains(point: EPoint) = contains(point.x, point.y)


}
