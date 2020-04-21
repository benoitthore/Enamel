package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.primitives.*

// TODO Refactor so it follows the same model as in Point, Rect and Size for mutablility
open class ECircle(open val center: EPoint = EPoint(), open val radius: Float = 0f) {

    constructor(
        center: EPoint,
        radius: Number
    ) : this(center, radius.toFloat())

    init {
        allocateDebugMessage()
    }

    open val x: Float get() = center.x

    open val y: Float get() = center.y

    fun toMutable() = ECircleMutable(center = center.toMutable(), radius = radius)
    fun toImmutable() = ECircle(center = center.toImmutable(), radius = radius)


    fun intersects(other: ECircle) =
        this.center.distanceTo(other.center) < other.radius + this.radius

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

    fun toListOfPoint(
        list: MutableList<EPointMutable>,
        startAt: EAngle? = null,
        distanceList: List<Number>? = null
    ): List<EPointMutable> {
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

    fun toListOfPoint(
        numberOfPoint: Int,
        startAt: EAngleMutable? = null,
        distanceList: List<Number>? = null
    ) =
        toListOfPoint(
            MutableList(numberOfPoint) { EPointMutable() }, startAt, distanceList
        )


    fun pointAtAnchor(
        x: Number,
        y: Number,
        target: EPointMutable = EPointMutable()
    ): EPointMutable {
        val origin = target.set(center.x - radius, center.y - radius)
        val size = radius * 2
        return EPointMutable(x = origin.x + size * x.f, y = origin.y + size * y.f)
    }

    fun pointAtAnchor(anchor: EPointMutable, target: EPointMutable = EPointMutable()) =
        pointAtAnchor(anchor.x, anchor.y, target)


    fun inset(margin: Number, target: ECircleMutable = ECircleMutable()) =
        target.set(center, radius - margin.f)

    fun expand(margin: Number, target: ECircleMutable = ECircleMutable()) = inset(-margin.f, target)

    fun offset(x: Number, y: Number, target: ECircleMutable = ECircleMutable()) =
        target.set(this.x + x.f, this.y + y.f, radius)

    fun offset(n: Number, target: ECircleMutable = ECircleMutable()) = offset(n, n, target)
    fun offset(point: EPointMutable, target: ECircleMutable = ECircleMutable()) =
        offset(point.x, point.y, target)


    fun scaledAnchor(
        to: Number, x: Number, y: Number,
        pointBuffer: EPointMutable = EPointMutable(),
        target: ECircleMutable = ECircleMutable()
    ) =
        scaledRelative(to, relativeTo = pointAtAnchor(x, y, pointBuffer), target = target)

    fun scaledAnchor(
        to: Number,
        anchor: EPoint = EPoint.half,
        pointBuffer: EPointMutable = EPointMutable(),
        target: ECircleMutable = ECircleMutable()
    ) =
        scaledAnchor(to, anchor.x, anchor.y, pointBuffer = pointBuffer, target = target)

    fun scaledRelative(
        to: Number,
        relativeTo: EPoint = EPoint.half,
        target: ECircleMutable = ECircleMutable()
    ): ECircleMutable {
        val to = to.f
        val newCenterX = center.x + (relativeTo.x - center.x) * (1 - to)
        val newCenterY = center.x + (relativeTo.x - center.x) * (1 - to)
        return target.set(newCenterX, newCenterY, radius * to)
    }

    fun resized(newRadius: Number, target: ECircleMutable = ECircleMutable()) =
        target.set(center = center, radius = newRadius)

    fun resizedBy(extraRadius: Number, target: ECircleMutable = ECircleMutable()) =
        target.set(center = center, radius = radius + extraRadius.f)


}

class ECircleMutable(
    override val center: EPointMutable = EPointMutable(),
    override var radius: Float = 0f
) :
    ECircle(center, radius), Resetable {
    constructor(center: EPointMutable = EPointMutable(), radius: Number) : this(center, radius.f)

    fun copy(
        x: Number = this.x,
        y: Number = this.y,
        radius: Number = this.radius,
        target: ECircleMutable = ECircleMutable()
    ) =
        ECircleMutable(center.copy(x = x, y = y, target = target.center), radius = radius)

    fun copy(
        center: EPoint,
        radius: Number = this.radius,
        target: ECircleMutable = ECircleMutable()
    ) = copy(x = center.x, y = center.y, radius = radius, target = target)

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

    fun set(other: ECircle) = set(other.center.x, other.center.y, other.radius)
    fun set(center: EPoint = this.center, radius: Number = this.radius) =
        set(center.x, center.y, radius)

    fun set(x: Number = this.x, y: Number = this.y, radius: Number = this.radius): ECircleMutable {
        this.center.set(x, y)
        this.radius = radius.f
        return this
    }


    fun selfInset(margin: Number) = inset(margin = margin, target = this)

    fun selfExpand(margin: Number) = expand(margin = margin, target = this)
    fun selfOffset(x: Number, y: Number) = offset(x = x, y = y, target = this)

    fun selfOffset(n: Number) = offset(n = n, target = this)
    fun selfOffset(point: EPointMutable) = offset(point = point, target = this)

    fun selfScaledAnchor(
        to: Number, x: Number, y: Number,
        pointBuffer: EPointMutable = EPointMutable()
    ) = scaledAnchor(to = to, x = x, y = y, target = this, pointBuffer = pointBuffer)

    fun selfScaledAnchor(
        to: Number,
        anchor: EPoint = EPoint.half,
        pointBuffer: EPointMutable = EPointMutable(),
        target: ECircleMutable = ECircleMutable()
    ) = scaledAnchor(
        to = to,
        anchor = anchor,
        pointBuffer = pointBuffer,
        target = target
    )

    fun selfScaledRelative(
        to: Number,
        relativeTo: EPoint = EPoint.half,
        target: ECircleMutable = ECircleMutable()
    ) = scaledRelative(
        to = to,
        relativeTo = relativeTo,
        target = target
    )

    fun selfResized(newRadius: Number) = resized(newRadius = newRadius, target = this)

    fun selfResizedBy(extraRadius: Number) = resizedBy(extraRadius = extraRadius, target = this)


}
