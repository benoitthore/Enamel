package com.benoitthore.enamel.geometry.figures.circle

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.svg.SVGContext

interface ECircle : EShape {
    val radius: Float
    val center: EPoint

    override fun _copy(): ECircle = Circle(this)

    override fun addTo(context: SVGContext) {
        context.oval(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

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

    fun contains(other: ECircle): Boolean = TODO()
}

interface ECircleMutable : ECircle, EShapeMutable {
    override var radius: Float
    override val center: EPointMutable

    override fun _copy(): ECircleMutable = MutableCircle(this)

    fun set(other: ECircle) = set(other.center.x, other.center.y, other.radius)
    fun set(center: EPoint = this.center, radius: Number = this.radius) =
        set(center.x, center.y, radius)

    fun set(
        x: Number = centerX,
        y: Number = centerY,
        radius: Number = this.radius
    ): ECircleMutable {
        this.centerX = x.f
        this.centerY = y.f
        this.radius = radius.f
        return this
    }


}

