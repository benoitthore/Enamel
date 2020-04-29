package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.*
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ECircle : ESVG {


    override fun addTo(context: SVGContext) {
        context.oval(x - radius, y - radius, x + radius, y + radius)
    }

    val center: EPoint
    val radius: Float


    val x: Float get() = center.x
    val y: Float get() = center.y

    fun toMutable() = E.CircleMutable(center = center.toMutable(), radius = radius)
    fun toImmutable() = E.circle(center = center.toImmutable(), radius = radius)

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

    fun pointAtAngle(angle: EAngle, target: EPointMutable): EPointMutable =
        target.set(angle, radius).selfOffset(center)

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

    fun toListOfPoint(
        distanceList: List<Number>,
        startAt: EAngle? = null
    ) =
        toListOfPoint(
            MutableList(distanceList.size) { E.PointMutable() }, startAt, distanceList
        )

    fun toListOfPoint(
        numberOfPoint: Int,
        startAt: EAngle? = null
    ) =
        toListOfPoint(
            MutableList(numberOfPoint) { E.PointMutable() }, startAt
        )


    fun pointAtAnchor(
        x: Number,
        y: Number,
        target: EPointMutable = E.PointMutable()
    ): EPointMutable {
        val origin = target.set(center.x - radius, center.y - radius)
        val size = radius * 2
        return E.PointMutable(x = origin.x + size * x.f, y = origin.y + size * y.f)
    }

    fun pointAtAnchor(anchor: EPointMutable, target: EPointMutable = E.PointMutable()) =
        pointAtAnchor(anchor.x, anchor.y, target)


    fun inset(margin: Number, target: ECircleMutable = E.CircleMutable()) =
        target.set(center, radius - margin.f)

    fun expand(margin: Number, target: ECircleMutable = E.CircleMutable()) = inset(-margin.f, target)

    fun offset(x: Number, y: Number, target: ECircleMutable = E.CircleMutable()) =
        target.set(this.x + x.f, this.y + y.f, radius)

    fun offset(n: Number, target: ECircleMutable = E.CircleMutable()) = offset(n, n, target)
    fun offset(point: EPointMutable, target: ECircleMutable = E.CircleMutable()) =
        offset(point.x, point.y, target)


    fun scaledAnchor(
        to: Number, x: Number, y: Number,
        pointBuffer: EPointMutable = E.PointMutable(),
        target: ECircleMutable = E.CircleMutable()
    ) =
        scaledRelative(to, relativeTo = pointAtAnchor(x, y, pointBuffer), target = target)

    fun scaledAnchor(
        to: Number,
        anchor: EPoint = E.Point.half,
        pointBuffer: EPointMutable = E.PointMutable(),
        target: ECircleMutable = E.CircleMutable()
    ) =
        scaledAnchor(to, anchor.x, anchor.y, pointBuffer = pointBuffer, target = target)

    fun scaledRelative(
        to: Number,
        relativeTo: EPoint,
        target: ECircleMutable = E.CircleMutable()
    ): ECircleMutable {
        val to = to.f
        val newCenterX = center.x + (relativeTo.x - center.x) * (1 - to)
        val newCenterY = center.x + (relativeTo.x - center.x) * (1 - to)
        return target.set(newCenterX, newCenterY, radius * to)
    }

    fun resized(newRadius: Number, target: ECircleMutable = E.CircleMutable()) =
        target.set(center = center, radius = newRadius)

    fun resizedBy(extraRadius: Number, target: ECircleMutable = E.CircleMutable()) =
        target.set(center = center, radius = radius + extraRadius.f)


}

interface ECircleMutable : ECircle, Resetable {
    class Impl internal constructor(centerX: Number, centerY: Number, radius: Number) :
        ECircleMutable {
        init {
            allocateDebugMessage()
        }

        override val center: EPointMutable = E.PointMutable(centerX, centerY)
        override var radius: Float = radius.toFloat()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true

            if (other !is Impl) return false

            if (center != other.center) return false
            if (radius != other.radius) return false

            return true
        }

        override fun hashCode(): Int {
            var result = center.hashCode()
            result = 31 * result + radius.hashCode()
            return result
        }

        override fun toString(): String {
            return "Circle(center=$center, radius=$radius)"
        }


    }

    override val center: EPointMutable
    override var radius: Float

    fun copy(
        x: Number = this.x,
        y: Number = this.y,
        radius: Number = this.radius,
        target: ECircleMutable = E.CircleMutable()
    ) =
        E.CircleMutable(center.copy(x = x, y = y, target = target.center), radius = radius)

    fun copy(
        center: EPoint,
        radius: Number = this.radius,
        target: ECircleMutable = E.CircleMutable()
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
        pointBuffer: EPointMutable = E.PointMutable()
    ) = scaledAnchor(to = to, x = x, y = y, target = this, pointBuffer = pointBuffer)

    fun selfScaledAnchor(
        to: Number,
        anchor: EPoint = E.Point.half,
        pointBuffer: EPointMutable = E.PointMutable(),
        target: ECircleMutable = E.CircleMutable()
    ) = scaledAnchor(
        to = to,
        anchor = anchor,
        pointBuffer = pointBuffer,
        target = target
    )

    fun selfScaledRelative(
        to: Number,
        relativeTo: EPoint,
        target: ECircleMutable = E.CircleMutable()
    ) = scaledRelative(
        to = to,
        relativeTo = relativeTo,
        target = target
    )

    fun selfResized(newRadius: Number) = resized(newRadius = newRadius, target = this)

    fun selfResizedBy(extraRadius: Number) = resizedBy(extraRadius = extraRadius, target = this)


}
