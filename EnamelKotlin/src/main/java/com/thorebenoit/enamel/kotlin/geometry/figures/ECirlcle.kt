package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*

open class ECircleImmutable(open val center: EPointImmutable = EPointImmutable(), open val radius: Float = 0f)
class ECircle(override val center: EPoint = EPoint(), override var radius: Float = 0f) :
    ECircleImmutable(center, radius) {
    constructor(center: EPoint, radius: Number) : this(center, radius.f)


    var x: Float
        get() = center.x
        set(value) {
            center.x = value
        }
    var y: Float
        get() = center.y
        set(value) {
            center.y = value
        }

    fun set(center: EPointImmutable, radius: Number) = set(center.x, center.y, radius)

    fun set(x: Number, y: Number, radius: Number): ECircle {
        this.center.set(x, y)
        this.radius = radius.f
        return this
    }

    fun pointsInList(list: MutableList<EPoint>, startAt: EAngleImmutable? = null): List<EPoint> {
        if (list.isEmpty()) { // Don't divide by zero
            return list
        }

        val degreesPerStep = 360f / list.size
        val extra = startAt?.degrees?.i ?: 0
        val fromAngle = 0f + extra
        val toAngle = 360f + extra


        var currAngle = fromAngle
        var i = 0
        while (i < list.size) {
            val angle = currAngle.degrees()
            val point = list[i]

            point.set(center)
            point.offsetAngle(angle, radius)

            currAngle += degreesPerStep
            i++
        }

        return list
    }

    fun toListOfPoint(numberOfPoint: Int, startAt: EAngle? = null) = pointsInList(
        MutableList(numberOfPoint) { EPoint() }, startAt
    )


    fun insetBy(margin: Number) = set(center, radius - margin.f)
    fun expandBy(margin: Number) = insetBy(-margin.f)

    private fun pointAtAnchor(x: Number, y: Number): EPoint {
        val origin = center.x - radius point center.y - radius
        val size = radius * 2
        return EPoint(x = origin.x + size * x.f, y = origin.y + size * y.f)
    }

    private fun pointAtAnchor(anchor: EPoint) = pointAtAnchor(anchor.x, anchor.y)
    fun scaledAnchor(to: Number, x: Number, y: Number) = scaledRelative(to, relativeTo = pointAtAnchor(x, y))
    fun scaledAnchor(to: Number, anchor: EPointImmutable = EPointImmutable.half) = scaledAnchor(to, anchor.x, anchor.y)

    fun scaledRelative(to: Number, relativeTo: EPointImmutable = EPointImmutable.half): ECircle {
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

fun EPoint.toCircles(radius: Number, buffer: ECircle = ECircle()): ECircle {
    buffer.center.set(this)
    buffer.radius = radius.f
    return buffer
}


fun List<EPoint>.toCircles(radius: Number, buffer: List<ECircle> = MutableList(size) { ECircle() }): List<ECircle> {
    forEachIndexed { i, p ->
        p.toCircles(radius, buffer[i])
    }
    return buffer
}

